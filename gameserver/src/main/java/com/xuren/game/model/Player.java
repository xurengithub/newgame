package com.xuren.game.model;

import core.newnetserver.NetManager;
import game.playerino.equipment.ItemEntity;
import game.playerino.equipment.ItemsManager;
import game.playerino.player.PlayerDao;
import game.playerino.player.PlayerDataEntity;
import game.playerino.player.PlayerEntity;
import game.playerino.player.PlayerManager;
import game.scene.Area;
import org.apache.mina.core.session.IoSession;
import proto.MsgBase;

import java.util.List;

public class Player{
    private PlayerDao playerDao;
    private ItemsManager itemsManager;

    public long playerId;
    public String scene;
    public int areaId;
    public Area area;
    public IoSession session;

    public PlayerEntity playerEntity;
    public List<ItemEntity> itemEntityList;
    public PlayerDataEntity playerDataEntity;
//    public playerData;
//    public positionData;
    public Player(IoSession session, PlayerDao playerDao, ItemsManager itemsManager){
        this.session = session;
        this.playerDao = playerDao;
        this.itemsManager = itemsManager;
    }

    public void send(MsgBase msgBase){
        NetManager.send(session, msgBase);
    }

    public void update(){
        playerDao.update(playerEntity);
        itemsManager.saveAll(itemEntityList);
    }

    public void close(){


        area.removePlayer(playerId);

        playerDao = null;
        itemsManager = null;
        PlayerManager.removePlayer(playerId);
        playerId = 0;
        scene = null;
        areaId = 0;
        area = null;
        session = null;

        playerEntity = null;
        itemEntityList = null;
        playerDataEntity = null;

    }
}