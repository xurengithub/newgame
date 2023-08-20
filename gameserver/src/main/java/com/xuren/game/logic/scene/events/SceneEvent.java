package com.xuren.game.logic.scene.events;


import com.xuren.game.logic.scene.options.Operation;

import java.util.List;

public class SceneEvent implements IEvent {
    private String rid;
    private List<Operation> operations;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
