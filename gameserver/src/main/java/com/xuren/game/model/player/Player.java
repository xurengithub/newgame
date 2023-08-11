package com.xuren.game.model.player;

import game.playerino.equipment.ItemEntity;

import java.util.List;

public class Player {
    public long playerId;
//    public PlayerDataEntity playerDataEntity;
    public PlayerEntity playerEntity;
    public List<ItemEntity> itemEntityList;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public List<ItemEntity> getItemEntityList() {
        return itemEntityList;
    }

    public void setItemEntityList(List<ItemEntity> itemEntityList) {
        this.itemEntityList = itemEntityList;
    }

    public void give(int type, int num){

    }
    public void cost(int type, int num){

    }
}
