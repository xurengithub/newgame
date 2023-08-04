package com.xuren.game.logic;

import com.google.api.client.util.Lists;
import com.xuren.game.logic.model.PlayerEntity;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author xuren
 */
public class OnlinePlayerManager {
    public static Map<String, PlayerEntity> onlinePlayerMap = Maps.newConcurrentMap();

    public static List<PlayerEntity> onlinePlayers() {
        return Lists.newArrayList(onlinePlayerMap.values());
    }

    public static PlayerEntity getOnlinePlayer(String rid) {
        return onlinePlayerMap.get(rid);
    }
}
