package com.xuren.game.logic.scene.systems.aoi;

import com.google.api.client.util.Lists;
import com.xuren.game.logic.scene.entities.PlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直管理格子内的内容
 * @author xuren
 */
public class Grid {
    private final Map<String, PlayerEntity> playerEntityMap = new HashMap<>();

    public void addPlayer(PlayerEntity playerEntity) {
        playerEntityMap.put(playerEntity.getRid(), playerEntity);
    }

    public void removePlayer(PlayerEntity playerEntity) {
        playerEntityMap.remove(playerEntity.getRid());
    }

    public List<PlayerEntity> getPlayers() {
        return Lists.newArrayList(playerEntityMap.values());
    }
}
