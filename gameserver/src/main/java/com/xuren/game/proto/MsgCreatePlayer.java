package com.xuren.game.proto;


import com.xuren.game.common.proto.MsgBase;
import com.xuren.game.model.player.PlayerEntity;

public class MsgCreatePlayer extends MsgBase {
    private String playerName;
    private long playerId;
    private int result;
    private PlayerEntity playerEntity;
    public MsgCreatePlayer(){
        protoName = "MsgCreatePlayer";
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
