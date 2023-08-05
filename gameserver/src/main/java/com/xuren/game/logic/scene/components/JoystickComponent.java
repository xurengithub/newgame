package com.xuren.game.logic.scene.components;

/**
 * @author xuren
 */
public class JoystickComponent {
    private int degree;
    private boolean open;

    public JoystickComponent(int degree, boolean open) {
        this.degree = degree;
        this.open = open;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public static JoystickComponent create() {
        return new JoystickComponent(0, false);
    }
}
