package com.xuren.game.logic.scene.syncmsg;

import com.xuren.game.logic.scene.components.TransformComponent;

import java.util.List;

/**
 * @author xuren
 */
public class AOIUpdateSyncMsg {
    private List<String> leaveRids;
    private List<TransformComponent> enterPlayers;

    public AOIUpdateSyncMsg(List<String> leaveRids, List<TransformComponent> enterPlayers) {
        this.leaveRids = leaveRids;
        this.enterPlayers = enterPlayers;
    }

    public List<String> getLeaveRids() {
        return leaveRids;
    }

    public void setLeaveRids(List<String> leaveRids) {
        this.leaveRids = leaveRids;
    }

    public List<TransformComponent> getEnterPlayers() {
        return enterPlayers;
    }

    public void setEnterPlayers(List<TransformComponent> enterPlayers) {
        this.enterPlayers = enterPlayers;
    }
}
