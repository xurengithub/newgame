package com.xuren.game.logic.scene;

import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.systems.aoi.GridManager;
import org.testng.collections.Maps;

import java.io.IOException;
import java.util.Map;

/**
 * @author xuren
 */
public abstract class SceneManager {
    private static final Map<String, Scene> sceneMap = Maps.newHashMap();

    public static void initScene() throws IOException {
        Scene scene = initS1();
        sceneMap.put(scene.getId(), scene);
    }

    public static Map<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public static Scene getScene(String sceneId) {
        return sceneMap.get(sceneId);
    }

    private static Scene initS1() throws IOException {
        Easy3dNav easy3dNav = new Easy3dNav();
        easy3dNav.setPrintMeshInfo(true);
        easy3dNav.init("Terrain6.obj");

        GridManager gridManager = new GridManager(1000, 1000, 100);
        Scene scene = new Scene();
        scene.init("1", easy3dNav, gridManager);
        return scene;
    }
}
