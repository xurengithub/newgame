package com.xuren.game.logic.scene.utils;

/**
 * @author xuren
 */
public abstract class EulerUtils {
    public static double[] fromDegree(float degree) {
        double radian = degree * Math.PI / 180;
        double x = Math.sin(radian);
        double z = Math.cos(radian);
        return new double[]{x, z};
    }

    public static double radian(float degree) {
        return degree * Math.PI /180;
    }

    public static double degree(double radian) {
        return radian * 180 / Math.PI;
    }
}
