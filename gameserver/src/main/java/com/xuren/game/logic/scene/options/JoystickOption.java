package com.xuren.game.logic.scene.options;

/**
 * @author xuren
 */
public class JoystickOption extends Operation{
    private boolean open;
    private float degree;
    private float cameraEulerY;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getCameraEulerY() {
        return cameraEulerY;
    }

    public void setCameraEulerY(float cameraEulerY) {
        this.cameraEulerY = cameraEulerY;
    }
}
