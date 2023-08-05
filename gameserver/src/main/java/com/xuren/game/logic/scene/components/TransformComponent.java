package com.xuren.game.logic.scene.components;

/**
 * @author xuren
 */
public class TransformComponent {
    private float x;
    private float y;
    private float z;
    private float eulerY;

    public TransformComponent(float x, float y, float z, float eulerY) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.eulerY = eulerY;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getEulerY() {
        return eulerY;
    }

    public void setEulerY(float eulerY) {
        this.eulerY = eulerY;
    }

    public static TransformComponent create(float x, float y, float z, float eulerY) {
        return new TransformComponent(x, y, z, eulerY);
    }
}
