package com.xuren.game.proto;

import com.xuren.game.model.equipment.ItemEntity;
import com.xuren.game.model.player.PlayerEntity;

public class MsgEnterGame extends MsgBase {
    private String playerName;
    private long playerId;
    private int result;
    private PlayerEntity[] playerEntities;
    private ItemEntity[] items;

    public MsgEnterGame(){
        protoName = protoName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public PlayerEntity[] getPlayerEntities() {
        return playerEntities;
    }

    public void setPlayerEntities(PlayerEntity[] playerEntities) {
        this.playerEntities = playerEntities;
    }

    public ItemEntity[] getItems() {
        return items;
    }

    public void setItems(ItemEntity[] items) {
        this.items = items;
    }
}
