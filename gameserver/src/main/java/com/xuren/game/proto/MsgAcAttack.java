package com.xuren.game.proto;

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
