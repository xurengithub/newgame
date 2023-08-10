package com.xuren.game.logic.scene.systems.aoi;

import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.entities.PlayerEntity;

/**
 * @author xuren
 */
public abstract class AOISystem {
    public static void update(PlayerEntity playerEntity, Scene scene) {
        scene.getGridManager().updateObj(playerEntity);
    }
}
