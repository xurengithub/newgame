package com.xuren.game.logic.scene.syncmsg;

import java.util.List;

/**
 * @author xuren
 */
public class PlayersLeaveGridMsg {
    private List<String> rids;

    public PlayersLeaveGridMsg(List<String> rids) {
        this.rids = rids;
    }

    public List<String> getRids() {
        return rids;
    }

    public void setRids(List<String> rids) {
        this.rids = rids;
    }
}
