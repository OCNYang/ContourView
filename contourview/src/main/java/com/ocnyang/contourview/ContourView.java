package com.ocnyang.contourview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/8/23 09:34.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class ContourView extends View {

    public final static int SHADER_MODE_NULL = 0x00;
    public final static int SHADER_MODE_RADIAL = 0x01;
    public final static int SHADER_MODE_SWEEP = 0x02;
    public final static int SHADER_MODE_LINEAR = 0x03;
    public final static int SHADER_MODE_CUSTOM = 0x04;
    private int mW;
    private int mH;
    private int mShaderColor;

    @IntDef({SHADER_MODE_NULL, SHADER_MODE_RADIAL, SHADER_MODE_SWEEP, SHADER_MODE_LINEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShaderMode {
    }

    public final static int SHADER_STYLE_LEFT_TO_BOTTOM = 0x00;
    public final static int SHADER_STYLE_RIGHT_TO_BOTTOM = 0x11;
    public final static int SHADER_STYLE_TOP_TO_BOTTOM = 0x12;
    public final static int SHADER_STYLE_CENTER = 0x13;

    @IntDef({SHADER_STYLE_LEFT_TO_BOTTOM, SHADER_STYLE_RIGHT_TO_BOTTOM, SHADER_STYLE_TOP_TO_BOTTOM, SHADER_STYLE_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShaderStyle {
    }

    public final static int STYLE_SAND = 0x00;
    public final static int STYLE_CLOUDS = 0x21;
    public final static int STYLE_RIPPLES = 0x22;
    public final static int STYLE_BEACH = 0x23;
    public final static int STYLE_SHELL = 0x25;
    private final static int STYLE_NULL = 0x24;

    @IntDef({STYLE_SAND, STYLE_CLOUDS, STYLE_RIPPLES, STYLE_BEACH, STYLE_SHELL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    public final static float SMOOTHNESS_DEF = 0.25F;
    //smoothing coefficient: 0 ~ 1 recommended range: 0.15 ~ 0.3
    private float mSmoothness = 0.25F;

    private int mShaderMode;
    private int mShaderStartColor;
    private int mShaderEndColor;
    private int mShaderStyle;
    private int mStyle;
    private Shader[] mShader;
    private List<Point[]> mPointsList;
    private Paint mPaint;

    public ContourView(Context context) {
        this(context, null);
    }

    public ContourView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContourView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ContourView);
        mStyle = typedArray.getInt(R.styleable.ContourView_contour_style, STYLE_SAND);

        float smn = typedArray.getFloat(R.styleable.ContourView_smoothness, SMOOTHNESS_DEF);
        if (smn <= 0) {
            mSmoothness = 0.1F;
        } else if (smn >= 1) {
            mSmoothness = 0.99F;
        } else {
            mSmoothness = smn;
        }

        int shader_mode = typedArray.getInt(R.styleable.ContourView_shader_mode, SHADER_MODE_NULL);
        mShaderMode = shader_mode;
        if (SHADER_MODE_NULL != shader_mode) {
            mShaderStartColor = typedArray.getColor(R.styleable.ContourView_shader_startcolor, Color.argb(90, 255, 255, 255));
            mShaderEndColor = typedArray.getColor(R.styleable.ContourView_shader_endcolor, Color.argb(90, 255, 255, 255));
            mShaderStyle = typedArray.getInt(R.styleable.ContourView_shader_style, SHADER_STYLE_LEFT_TO_BOTTOM);
        } else {
            mShaderColor = typedArray.getColor(R.styleable.ContourView_shader_color, Color.argb(90, 255, 255, 255));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean haveShader = false;
        if (mShaderMode == SHADER_MODE_NULL) {
            mPaint.setColor(mShaderColor);
        } else {
            haveShader = true;
        }

        if (mStyle != STYLE_NULL) {
            mPointsList = PointsFactory.getPoints(mStyle, mW, mH);
        }
        int flag = 0;
        drawcontour:
        for (Point[] pts : mPointsList) {
            ++flag;
            int length = pts.length;
            if (length < 4) {
                continue drawcontour;
            }
            Path path = new Path();
            int x_min = 0, y_min = 0, x_max = 0, y_max = 0;
            for (int i = 0; i < length; i++) {
                Point p_i, p_1i, p_i1, p_i2;
                float ai_x, ai_y, bi_x, bi_y;

                p_i = pts[i];
                if (i == 0) {
                    path.moveTo(pts[0].x, pts[0].y);
                    x_max = x_min = pts[0].x;
                    y_min = y_max = pts[0].y;
                    p_1i = pts[length - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[i + 2];
                } else if (i == length - 1) {
                    p_1i = pts[i - 1];
                    p_i1 = pts[0];
                    p_i2 = pts[1];
                } else if (i == length - 2) {
                    p_1i = pts[i - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[0];
                } else {
                    p_1i = pts[i - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[i + 2];
                }

                if (p_1i == null || p_i == null || p_i1 == null || p_i2 == null) {
                    continue drawcontour;
                }

                ai_x = p_i.x + (p_i1.x - p_1i.x) * mSmoothness;
                ai_y = p_i.y + (p_i1.y - p_1i.y) * mSmoothness;

                bi_x = p_i1.x - (p_i2.x - p_i.x) * mSmoothness;
                bi_y = p_i1.y - (p_i2.y - p_i.y) * mSmoothness;

                path.cubicTo(ai_x, ai_y, bi_x, bi_y, p_i1.x, p_i1.y);

                if (pts[i].x < x_min) {
                    x_min = pts[i].x;
                }
                if (pts[i].x > x_max) {
                    x_max = pts[i].x;
                }
                if (pts[i].y < y_min) {
                    y_min = pts[i].y;
                }
                if (pts[i].y > y_max) {
                    y_max = pts[i].y;
                }
            }

            if (haveShader) {
                if (mShaderMode == SHADER_MODE_CUSTOM && mShader != null) {
                    mPaint.setShader(mShader[(flag - 1) % mShader.length]);
                } else {
                    Point startPoint, endPoint;
                    switch (mShaderStyle) {
                        case SHADER_STYLE_LEFT_TO_BOTTOM:
                            startPoint = new Point(x_min, y_min);
                            endPoint = new Point(x_max, y_max);
                            break;
                        case SHADER_STYLE_RIGHT_TO_BOTTOM:
                            startPoint = new Point(x_max, y_min);
                            endPoint = new Point(x_min, y_max);
                            break;
                        case SHADER_STYLE_TOP_TO_BOTTOM:
                            startPoint = new Point((x_max - x_min) / 2 + x_min, y_min);
                            endPoint = new Point(x_min, (y_max - y_min) / 2 + y_min);
                            break;
                        case SHADER_STYLE_CENTER:
                            startPoint = new Point((x_max - x_min) / 2 + x_min, (y_max - y_min) / 2 + y_min);
                            endPoint = new Point(x_max, (y_max - y_min) / 2 + y_min);
                            break;
                        default:
                            startPoint = new Point(0, 0);
                            endPoint = new Point(mW, mH);
                            break;
                    }

                    switch (mShaderMode) {
                        case SHADER_MODE_RADIAL:
                            RadialGradient radialGradient = new RadialGradient(
                                    startPoint.x, endPoint.y,
                                    (int) (Math.sqrt(Math.pow(Math.abs(x_max - x_min), 2) + Math.pow((Math.abs(y_max - y_min)), 2))),
                                    mShaderStartColor,
                                    mShaderEndColor,
                                    Shader.TileMode.CLAMP);
                            mPaint.setShader(radialGradient);
                            break;
                        case SHADER_MODE_SWEEP:
                            SweepGradient sweepGradient = new SweepGradient(startPoint.x, startPoint.y, mShaderStartColor, mShaderEndColor);
                            mPaint.setShader(sweepGradient);
                            break;
                        case SHADER_MODE_LINEAR:
                            LinearGradient linearGradient = new LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y,
                                    mShaderStartColor, mShaderEndColor,
                                    Shader.TileMode.REPEAT);
                            mPaint.setShader(linearGradient);
                            break;
                    }
                }
            }
            canvas.drawPath(path, mPaint);
        }
        flag = 0;
    }


    //----------get or set---------------------------------------------------------------------------

    public void setPoints(Point... points) {
        mPointsList = new ArrayList<>(1);
        mPointsList.add(points);
        mStyle = STYLE_NULL;
    }

    public void setPoints(int... pts) {
        this.setPoints(ptsArrTopoints(pts));
    }

    public void setPoints(Point[]... pointsArr) {
        mPointsList = Arrays.asList(pointsArr);
        mStyle = STYLE_NULL;
    }

    public void setPoints(int[]... ptsArr) {
        mPointsList = new ArrayList<>();
        for (int[] pts : ptsArr) {
            mPointsList.add(ptsArrTopoints(pts));
        }
        mStyle = STYLE_NULL;
    }

    public Point[] ptsArrTopoints(int... pts) {
        Point[] points = new Point[(pts.length) / 2];
        for (int i = 0, j = 0; i < pts.length && j < points.length; i += 2, j++) {
            points[j] = new Point(pts[i], pts[i + 1]);
        }
        return points;
    }

    public int getShaderMode() {
        return mShaderMode;
    }

    public void setShaderMode(@ShaderMode int shaderMode) {
        mShaderMode = shaderMode;
    }

    public int getShaderStartColor() {
        return mShaderStartColor;
    }

    public void setShaderStartColor(@ColorInt int shaderStartColor) {
        mShaderStartColor = shaderStartColor;
    }

    public int getShaderEndColor() {
        return mShaderEndColor;
    }

    public void setShaderEndColor(@ColorInt int shaderEndColor) {
        mShaderEndColor = shaderEndColor;
    }

    public int getShaderStyle() {
        return mShaderStyle;
    }

    public void setShaderStyle(@ShaderStyle int shaderStyle) {
        mShaderStyle = shaderStyle;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(@Style int style) {
        mStyle = style;
    }

    public Shader[] getShader() {
        return mShader;
    }

    public void setShader(Shader... shader) {
        mShader = shader;
        mShaderMode = SHADER_MODE_CUSTOM;
    }

    public float getSmoothness() {
        return mSmoothness;
    }

    public void setSmoothness(float smoothness) {
        mSmoothness = smoothness;
    }

    public int getShaderColor() {
        return mShaderColor;
    }

    public void setShaderColor(int shaderColor) {
        mShaderColor = shaderColor;
    }
}
