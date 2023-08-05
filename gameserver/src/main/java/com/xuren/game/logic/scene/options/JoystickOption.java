package com.xuren.game.logic.scene.options;

/**
 * @author xuren
 */
public class JoystickOption extends Option{
    private boolean open;
    private int degree;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
