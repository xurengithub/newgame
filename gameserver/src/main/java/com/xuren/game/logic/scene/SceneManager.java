package com.xuren.game.logic.scene;

import com.xuren.game.logic.scene.nav.Easy3dNav;
import org.testng.collections.Maps;

import java.io.IOException;
import java.util.Map;

/**
 * @author xuren
 */
public abstract class SceneManager {
    private static Map<String, Scene> sceneMap = Maps.newHashMap();

    public static void initScene() throws IOException {
        Easy3dNav easy3dNav = new Easy3dNav();
        easy3dNav.setPrintMeshInfo(true);
        easy3dNav.init("dungeon.obj");
        Scene scene = new Scene();
        scene.init("1", easy3dNav);
        sceneMap.put("1", scene);
    }

    public static Map<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public static Scene getScene(String sceneId) {
        return sceneMap.get(sceneId);
    }
}
