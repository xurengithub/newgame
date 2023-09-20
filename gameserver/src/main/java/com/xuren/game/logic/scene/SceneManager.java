package com.xuren.game.logic.scene;

import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.systems.aoi.GridManager;
import org.springframework.util.StringUtils;
import org.testng.collections.Maps;

import java.io.IOException;
import java.util.Map;

/**
 * @author xuren
 */
public abstract class SceneManager {
    private static final Map<String, Scene> sceneMap = Maps.newHashMap();
    private static String defaultSceneId;

    public static void initScene() throws IOException {
        Scene scene = initS1();
        sceneMap.put(scene.getId(), scene);
        defaultSceneId = scene.getId();
    }

    public static Map<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public static Scene getScene(String sceneId) {
        return sceneMap.get(sceneId);
    }

    public static void enterDefaultScene(PlayerEntity player) {
        sceneMap.get(defaultSceneId).enter(player);
    }

    public static boolean inScene(PlayerEntity player) {
        return sceneMap.get(player.getSceneId()).inScene(player.getRid());
    }

    private static Scene initS1() throws IOException {
        Easy3dNav easy3dNav = new Easy3dNav();
        easy3dNav.setPrintMeshInfo(true);
        easy3dNav.init("dungeon.obj");

        GridManager gridManager = new GridManager(1000, 1000, 100);
        Scene scene = new Scene();
        scene.init("1", easy3dNav, gridManager);
        return scene;
    }

    public static void initInScene(PlayerEntity playerEntity) {
        if (!StringUtils.hasText(playerEntity.getSceneId())) {
            enterDefaultScene(playerEntity);
        } else {
            if (!SceneManager.inScene(playerEntity)) {
                getScene(playerEntity.getSceneId()).enter(playerEntity);
            }
        }
    }

    public static void leave(PlayerEntity playerEntity) {
        if (StringUtils.hasText(playerEntity.getSceneId())) {
            sceneMap.get(playerEntity.getSceneId()).leave(playerEntity.getRid());
        }
    }
}
