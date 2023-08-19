package com.xuren.game.logic.scene.entities;

import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.systems.aoi.Grid;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("role")
public class PlayerEntity {
    @Id
    private String rid;
    @Transient
    private JoystickComponent joystickComponent;
    private HealthComponent healthComponent;

    // region 场景相关
    @Transient
    private SceneState state;
    private TransformComponent transformComponent;
    @Transient
    private AStarComponent aStarComponent;
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

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
}
