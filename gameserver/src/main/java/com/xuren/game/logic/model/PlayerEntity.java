package com.xuren.game.logic.model;

import com.xuren.game.logic.TransformComponent;

public class PlayerEntity {
    private String rid;
    private TransformComponent transformComponent;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    public void setTransformComponent(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
    }
}
