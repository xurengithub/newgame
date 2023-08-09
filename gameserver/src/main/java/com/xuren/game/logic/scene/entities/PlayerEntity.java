package com.xuren.game.logic.scene.entities;

import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.AStarComponent;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.JoystickComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.systems.aoi.Grid;

public class PlayerEntity {
    private String rid;
    private SceneState state;
    private TransformComponent transformComponent;
    private JoystickComponent joystickComponent;
    private AStarComponent aStarComponent;
    private HealthComponent healthComponent;
    private Grid grid;

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

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
