package com.xuren.game.logic.scene.options;

/**
 * @author xuren
 */
public enum OperationType {
    /**
     * 摇杆
     */
    JOYSTICK(0),

    /**
     * FindWay
     */
    FIND_WAY(1),;

    OperationType(int type) {
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
