package com.ocnyang.contourviewdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ocnyang.contourview.ContourView;

public class ContourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initBeachContourView();
        initCustomContourView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/OCNYang/ContourView");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    /**
     * Customize the coordinates of the anchor to control the area to be drawn。
     */
    private void initCustomContourView() {
        ContourView contourViewCustom = (ContourView) findViewById(R.id.contourview_custom);
        int width = getWidth();
        int hight = 700;
        int[] ints = {width / 2, 50, ((int) (width * 0.75)), hight / 2, ((int) (width * 0.35)), 350};
        int[] intArr = new int[]{width / 5, hight / 3, width / 4 * 3, hight / 2, width / 2, ((int) (hight * 0.9)), width / 4, ((int) (hight * 0.75))};
        contourViewCustom.setPoints(ints, intArr);
        contourViewCustom.setShaderStartColor(getResources().getColor(R.color.startcolor));
        contourViewCustom.setShaderEndColor(getResources().getColor(R.color.endcolor));
        contourViewCustom.setShaderMode(ContourView.SHADER_MODE_RADIAL);
//        contourViewCustom.invalidate();
    }

    /**
     * Controls the color of the drawing.
     */
    private void initBeachContourView() {
        ContourView contourViewBeach = ((ContourView) findViewById(R.id.contourview_beach));

        RadialGradient radialGradient = new RadialGradient(
                0, 0,
                4000,
                getResources().getColor(R.color.startcolor),
                getResources().getColor(R.color.endcolor),
                Shader.TileMode.CLAMP);
        LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), 400,
                Color.argb(30, 255, 255, 255), Color.argb(90, 255, 255, 255),
                Shader.TileMode.REPEAT);
        contourViewBeach.setShader(radialGradient, linearGradient);
    }

    public int getWidth() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        return width;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
