package com.xuren.game.proto;

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
