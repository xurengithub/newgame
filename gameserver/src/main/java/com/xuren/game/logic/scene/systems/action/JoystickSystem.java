package com.xuren.game.logic.scene.systems.action;

import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;

public class JoystickSystem {
    public static void update(PlayerEntity playerEntity) {
        JoystickComponent joystickComponent = playerEntity.getJoystickComponent();
        TransformComponent transformComponent = playerEntity.getTransformComponent();

        float vx = 0;
        float vy = 0;
        float vz = 0;
        transformComponent.getEulerY();
    }
}
