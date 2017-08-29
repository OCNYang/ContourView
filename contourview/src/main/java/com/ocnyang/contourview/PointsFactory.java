package com.ocnyang.contourview;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

import static com.ocnyang.contourview.ContourView.STYLE_BEACH;
import static com.ocnyang.contourview.ContourView.STYLE_CLOUDS;
import static com.ocnyang.contourview.ContourView.STYLE_RIPPLES;
import static com.ocnyang.contourview.ContourView.STYLE_SAND;
import static com.ocnyang.contourview.ContourView.STYLE_SHELL;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/8/23 14:53.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public final class PointsFactory {
    public static List<Point[]> getPoints(@ContourView.Style int style, int width, int hight) {
        switch (style) {
            case STYLE_SAND:
                return getStyleSandPoints(width, hight);
            case STYLE_BEACH:
                return getStyleBeachPoints(width, hight);
            case STYLE_CLOUDS:
                return getStyleCloudsPoints(width, hight);
            case STYLE_RIPPLES:
                return getStyleRipplesPoints(width, hight);
            case STYLE_SHELL:
                return getStyleShellPoints(width, hight);
            default:
                return null;
        }
    }

    public static List<Point[]> getStyleSandPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        Point[] pts1 = new Point[4];
        Point[] pts2 = new Point[4];
        pts1[0] = new Point(0, hight / 6);
        pts1[1] = new Point((int) (width * 0.9), hight / 5);
        pts1[2] = new Point(width / 2, (int) (hight * (5.0 / 6)));
        pts1[3] = new Point(0, (int) (hight * 0.75));

        pts2[0] = new Point(width / 3, 0);
        pts2[1] = new Point(width / 2, hight / 3);
        pts2[2] = new Point(width, (int) (hight * 0.4));
        pts2[3] = new Point(width, 0);

        points.add(pts1);
        points.add(pts2);
        return points;
    }

    public static List<Point[]> getStyleBeachPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        if (width > hight) {
            Point[] pts1 = new Point[7];
            Point[] pts2 = new Point[7];
            pts1[0] = new Point((int) (width * 0.25), 0);
            pts1[1] = new Point((int) (width * 0.166666667), (int) (hight * 0.25));
            pts1[2] = new Point(width / 3, (int) (hight * 0.375));
            pts1[3] = new Point((int) (width * 0.416666667), (int) (hight * 0.625));
            pts1[4] = new Point((int) (width * 0.666666667), (int) (hight * 0.875));
            pts1[5] = new Point(width, (int) (hight * 0.75));
            pts1[6] = new Point(width, 0);

            int distance = (int) (width * 0.05);
            for (int i = 0; i < pts1.length; i++) {
                if (i == 0 || i == pts1.length - 2) {
                    pts2[i] = new Point(pts1[i].x + (i == 0 ? distance : 0), pts1[i].y - (i == pts1.length - 2 ? distance : 0));
                } else {
                    pts2[i] = new Point(pts1[i].x + distance, pts1[i].y - distance);
                }
            }
            points.add(pts1);
            points.add(pts2);

            return points;
        } else {
            Point[] pts1 = new Point[8];
            Point[] pts2 = new Point[8];
            pts1[0] = new Point(0, (int) (hight * 0.75));
            pts1[1] = new Point((int) (width * 0.25), (int) (hight * 0.83333333));
            pts1[2] = new Point((int) (width * 0.375), (int) (hight * 0.666666667));
            pts1[3] = new Point((int) (width * 0.625), (int) (hight * 0.583333333));
            pts1[4] = new Point((int) (width * 0.875), (int) (hight * 0.333333333));
            pts1[5] = new Point(width, (int) (hight * 0.0833333333));
            pts1[6] = new Point(width, 0);
            pts1[7] = new Point(0, 0);

            int distance = (int) (width * 0.05);
            for (int i = 0; i < pts1.length; i++) {
                if (i == pts1.length - 2 || i == pts1.length - 1) {
                    pts2[i] = pts1[i];
                } else {
                    pts2[i] = new Point(pts1[i].x + (i == 0 ? 0 : distance), pts1[i].y + distance);
                }
            }
            points.add(pts2);
            points.add(pts1);

            return points;
        }
    }

    public static List<Point[]> getStyleCloudsPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        int high = width < hight ? width : hight;
        Point[] pts1 = new Point[4];
        Point[] pts2 = new Point[4];
        Point[] pts3 = new Point[4];

        pts1[0] = new Point(0, 0);
        pts1[1] = new Point(0, (int) (high * 0.45));
        pts1[2] = new Point((int) (width * 0.916666667), 0);
        pts1[3] = new Point(0, 0);

        pts2[0] = new Point(0, 0);
        pts2[1] = new Point(0, (int) (high * 0.25));
        pts2[2] = new Point((int) (width * 0.75), 0);
        pts2[3] = new Point(0, 0);

        pts3[1] = new Point((int) (width * 0.58333333), 0);
        pts3[2] = new Point(width, (int) (high * 0.333333));
        pts3[0] = new Point(width, 0);
        pts3[3] = new Point(width, 0);

        points.add(pts2);
        points.add(pts3);
        points.add(pts1);

        return points;
    }

    public static List<Point[]> getStyleRipplesPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        Point[] pts1 = new Point[4];
        Point[] pts2 = new Point[4];
        Point[] pts3 = new Point[4];
        Point[] pts4 = new Point[4];
        Point[] pts5 = new Point[4];

        int radius = width / 6;
        pts1[0] = new Point(width / 2 - radius * 2, hight / 2);
        pts1[1] = new Point(width / 2, hight / 2 + radius);
        pts1[2] = new Point(width / 2 + radius * 2, hight / 2);
        pts1[3] = new Point(width / 2, hight / 2 - radius);

        int radius2 = width / 4;
        pts2[0] = new Point(width / 2 - radius2 * 2, hight / 2);
        pts2[1] = new Point(width / 2, hight / 2 + radius2);
        pts2[2] = new Point(width / 2 + radius2 * 2, hight / 2);
        pts2[3] = new Point(width / 2, hight / 2 - radius2);

        int radius3 = width / 3;
        pts3[0] = new Point(width / 2 - radius3 * 2, hight / 2);
        pts3[1] = new Point(width / 2, hight / 2 + radius3);
        pts3[2] = new Point(width / 2 + radius3 * 2, hight / 2);
        pts3[3] = new Point(width / 2, hight / 2 - radius3);

        int radius4 = width / 5;
        pts4[0] = new Point(width / 2 - radius4 * 2, hight / 2);
        pts4[1] = new Point(width / 2, hight / 2 + radius4);
        pts4[2] = new Point(width / 2 + radius4 * 2, hight / 2);
        pts4[3] = new Point(width / 2, hight / 2 - radius4);

        int radius5 = width / 2;
        pts5[0] = new Point(width / 2 - radius5 * 2, hight / 2);
        pts5[1] = new Point(width / 2, hight / 2 + radius5);
        pts5[2] = new Point(width / 2 + radius5 * 2, hight / 2);
        pts5[3] = new Point(width / 2, hight / 2 - radius5);

        points.add(pts5);
        points.add(pts3);
        points.add(pts2);
        points.add(pts4);
        points.add(pts1);
        return points;
    }

    public static List<Point[]> getStyleShellPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        Point[] pts1 = new Point[4];
        Point[] pts2 = new Point[4];
        Point[] pts3 = new Point[4];

        int radius = width / 6;
        pts1[0] = new Point(width / 2 - radius * 2, hight / 2);
        pts1[1] = new Point(width / 2, hight / 2 + radius);
        pts1[2] = new Point(width / 2 + radius * 2, hight / 2);
        pts1[3] = new Point(width / 2, hight / 2 - radius);

        int radius2 = width / 4;
        pts2[0] = new Point(width / 2 - radius2 * 2, hight / 2);
        pts2[1] = new Point(width / 2, hight / 2 + radius2);
        pts2[2] = new Point(width / 2 + radius * 2, hight / 2);
        pts2[3] = new Point(width / 2, hight / 2 - radius2);

        int radius3 = width / 3;
        pts3[0] = new Point(width / 2 - radius3 * 2, hight / 2);
        pts3[1] = new Point(width / 2, hight / 2 + radius3);
        pts3[2] = new Point(width / 2 + radius * 2, hight / 2);
        pts3[3] = new Point(width / 2, hight / 2 - radius3);

        points.add(pts3);
        points.add(pts2);
        points.add(pts1);
        return points;
    }
}
