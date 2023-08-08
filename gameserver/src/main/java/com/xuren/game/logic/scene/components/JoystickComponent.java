package com.xuren.game.logic.scene.components;

/**
 * @author xuren
 */
public class JoystickComponent {
    private boolean open;

    public JoystickComponent(boolean open) {
        this.open = open;
    }



    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public static JoystickComponent create() {
        return new JoystickComponent(false);
    }
}
