package com.xuren.game.proto;

import com.xuren.game.common.proto.MsgBase;

public class MsgOutGame extends MsgBase {
    private long playerId;
    public MsgOutGame(){
        protoName = "MsgOutGame";
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
