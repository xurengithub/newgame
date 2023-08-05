package com.xuren.game.logic.scene.components;

/**
 * @author xuren
 */
public class HealthComponent {
    private int hp;
    private int mp;
    public HealthComponent(int hp, int mp) {
        this.hp = hp;
        this.mp = mp;
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

    public static HealthComponent create(int hp, int mp) {
        return new HealthComponent(hp, mp);
    }
}
