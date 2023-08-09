package com.xuren.game.logic.scene.systems.aoi;

import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.entities.PlayerEntity;

/**
 * @author xuren
 */
public abstract class AOISystem {
    public static void update(PlayerEntity playerEntity, Scene scene) {
        // 1.判断当前所属格子是否和之前格子相同，不同需要更换格子
        int[] gridPos = scene.getGridManager().belongGridPos(playerEntity);
        // 格子有变动
        if (playerEntity.getGridX() != gridPos[0] || playerEntity.getGridZ() != gridPos[1]) {
            // 将角色从老格子移除
            scene.getGridManager().removeObj(playerEntity);
            // 将角色加入新格子
            scene.getGridManager().addObj(playerEntity);
        }
    }
}
