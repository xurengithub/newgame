package com.xuren.game.logic.scene.syncmsg;

/**
 * @author xuren
 */
public class LeaveSceneSyncMsg {
    private String rid;

    public LeaveSceneSyncMsg(String rid) {
        this.rid = rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
