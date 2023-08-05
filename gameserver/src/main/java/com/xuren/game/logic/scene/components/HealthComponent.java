package com.xuren.game.logic.scene.components;

/**
 * @author xuren
 */
public class HealthComponent {
    private int hp;
    private int mp;
    private float speed;

    public HealthComponent(int hp, int mp, float speed) {
        this.hp = hp;
        this.mp = mp;
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public static HealthComponent create(int hp, int mp, int speed) {
        return new HealthComponent(hp, mp, speed);
    }
}
