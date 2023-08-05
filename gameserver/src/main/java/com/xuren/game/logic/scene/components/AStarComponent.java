package com.xuren.game.logic.scene.components;

import org.recast4j.detour.extras.Vector3f;

import java.util.List;

/**
 * @author xuren
 */
public class AStarComponent {
    private List<Vector3f> roadPoints;
    private int nextStep;
    private double nowTime;
    private double totalTime;
    private float vx;
    private float vy;
    private float vz;

    public List<Vector3f> getRoadPoints() {
        return roadPoints;
    }

    public void setRoadPoints(List<Vector3f> roadPoints) {
        this.roadPoints = roadPoints;
    }

    public int getNextStep() {
        return nextStep;
    }

    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
    }

    public double getNowTime() {
        return nowTime;
    }

    public void setNowTime(double nowTime) {
        this.nowTime = nowTime;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getVz() {
        return vz;
    }

    public void setVz(float vz) {
        this.vz = vz;
    }
}
