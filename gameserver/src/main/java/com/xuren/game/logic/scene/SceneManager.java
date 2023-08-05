package com.xuren.game.logic.scene;

import com.xuren.game.logic.scene.maps.Map1;
import org.testng.collections.Maps;

import java.util.Map;

/**
 * @author xuren
 */
public abstract class SceneManager {
    private Map<String, Scene> sceneMap = Maps.newHashMap();



    private static void initScene() {
        new Scene().init("艾欧尼亚", Map1.mapData, Map1.mapWidth, Map1.mapHeight);
    }
}
