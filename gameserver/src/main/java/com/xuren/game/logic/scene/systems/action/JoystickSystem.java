package com.xuren.game.logic.scene.systems.action;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.nav.Easy3dNav;
import com.xuren.game.logic.scene.utils.EulerUtils;
import org.recast4j.detour.extras.Vector3f;

public abstract class JoystickSystem {
    public static void update(PlayerEntity playerEntity, Scene scene, Easy3dNav easy3dNav) {
        TransformComponent transformComponent = playerEntity.getTransformComponent();
        Vector3f pos = transformComponent.getPosition();

        float distance = scene.deltaTime * transformComponent.getSpeed();
        double[] arr = EulerUtils.fromDegree(transformComponent.getEulerY());
        double x = distance * arr[0];
        double z = distance * arr[1];
        pos.z += z;
        pos.x += x;
        pos.y = easy3dNav.findHeight(pos);
        Log.data.info("playerComponent:{}", JSON.toJSONString(pos));
    }
}
