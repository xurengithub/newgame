package com.xuren.game.logic;

public class SceneEvent implements IEvent{
    private String rid;
    private int eventType;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
