package com.xuren.game.logic;

/**
 * @author xuren
 */
public enum SceneEventType {
    /**
     * 摇杆
     */
    JOYSTICK(0);

    SceneEventType(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
