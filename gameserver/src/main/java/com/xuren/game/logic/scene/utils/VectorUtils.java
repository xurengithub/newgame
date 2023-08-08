package com.xuren.game.logic.scene.utils;

import org.recast4j.detour.extras.Vector3f;

/**
 * @author xuren
 */
public abstract class VectorUtils {
    public static Vector3f cloneVector(Vector3f vector3f) {
        return new Vector3f(vector3f.x, vector3f.y, vector3f.z);
    }

    public static Vector3f create(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    public static Vector3f zero() {
        return new Vector3f(0, 0, 0);
    }

    public static double distance(Vector3f start, Vector3f end) {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2) + Math.pow(end.z - start.z, 2));
    }

    public static Vector3f sub(Vector3f v1, Vector3f v2) {
        return new Vector3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public static float getLengthSQ(Vector3f v) {
        return v.x * v.x + v.y * v.y + v.z * v.z;
    }

    public static double getLength(Vector3f v) {
        return Math.sqrt(getLengthSQ(v));
    }

    public static Vector3f normalize(Vector3f v) {
        float len = (float) getLength(v);
        v.x = v.x / len;
        v.y = v.y / len;
        v.z = v.z / len;
        return v;
    }
}
