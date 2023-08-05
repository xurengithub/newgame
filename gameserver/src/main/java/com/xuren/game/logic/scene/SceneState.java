package com.xuren.game.logic.scene;

/**
 * @author xuren
 */
public enum SceneState {
    IDLE(0),
    RUN(1);

    SceneState(int state) {
        this.state = state;
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
