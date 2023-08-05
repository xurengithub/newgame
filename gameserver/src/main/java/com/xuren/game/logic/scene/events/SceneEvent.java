package com.xuren.game.logic.scene.events;

import com.xuren.game.logic.scene.options.Option;

import java.util.List;

public class SceneEvent implements IEvent {
    private String rid;
    private List<Option> options;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
