package com.xuren.game.proto;

import com.xuren.game.common.proto.MsgBase;

public class MsgAcAttack extends MsgBase {
    private long playerId;
    public MsgAcAttack(){
        protoName = "MsgAcAttack";
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
