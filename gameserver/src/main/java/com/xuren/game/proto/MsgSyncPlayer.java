package com.xuren.game.proto;

import com.xuren.game.common.proto.MsgBase;

public class MsgSyncPlayer extends MsgBase {
    private long playerId;
    private float x;
    private float y;
    private float z;
    private float ex;
    private float ey;
    private float ez;
    public MsgSyncPlayer(){
        protoName = "MsgSyncPlayer";
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getEx() {
        return ex;
    }

    public void setEx(float ex) {
        this.ex = ex;
    }

    public float getEy() {
        return ey;
    }

    public void setEy(float ey) {
        this.ey = ey;
    }

    public float getEz() {
        return ez;
    }

    public void setEz(float ez) {
        this.ez = ez;
    }
}
