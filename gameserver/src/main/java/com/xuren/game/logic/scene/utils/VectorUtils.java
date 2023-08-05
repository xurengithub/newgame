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
}
