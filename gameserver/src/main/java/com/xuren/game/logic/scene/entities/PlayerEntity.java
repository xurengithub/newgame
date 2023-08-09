package com.xuren.game.logic.scene.entities;

import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.systems.aoi.Grid;

public class PlayerEntity {
    private String rid;
    private JoystickComponent joystickComponent;
    private HealthComponent healthComponent;

    // region 场景相关
    private SceneState state;
    private TransformComponent transformComponent;
    private AStarComponent aStarComponent;
    private int gridX;
    private int gridZ;
    private String sceneId;
    // endregion

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public SceneState getState() {
        return state;
    }

    public void setState(SceneState state) {
        this.state = state;
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    public void setTransformComponent(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
    }

    public JoystickComponent getJoystickComponent() {
        return joystickComponent;
    }

    public void setJoystickComponent(JoystickComponent joystickComponent) {
        this.joystickComponent = joystickComponent;
    }

    public AStarComponent getaStarComponent() {
        return aStarComponent;
    }

    public void setaStarComponent(AStarComponent aStarComponent) {
        this.aStarComponent = aStarComponent;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public void setHealthComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridZ() {
        return gridZ;
    }

    public void setGridZ(int gridZ) {
        this.gridZ = gridZ;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
