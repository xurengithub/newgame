package com.xuren.game.logic.scene.syncmsg;

import com.xuren.game.logic.scene.components.TransformComponent;

/**
 * @author xuren
 */
public class TransformSyncMsg {
    private String rid;
    private TransformComponent transformComponent;

    public TransformSyncMsg(String rid, TransformComponent transformComponent) {
        this.rid = rid;
        this.transformComponent = transformComponent;
    }

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
