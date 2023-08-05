package com.xuren.game.logic.scene.components;

import org.recast4j.detour.extras.Vector3f;

/**
 * @author xuren
 */
public class TransformComponent {
    private Vector3f position;
    private float eulerY;
    private float speed;

    public TransformComponent(Vector3f position, float eulerY, float speed) {
        this.position = position;
        this.eulerY = eulerY;
        this.speed = speed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getEulerY() {
        return eulerY;
    }

    public void setEulerY(float eulerY) {
        this.eulerY = eulerY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TransformComponent create(Vector3f vector3f, float eulerY, float speed) {
        return new TransformComponent(vector3f, eulerY, speed);
    }
}
