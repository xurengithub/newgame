package com.xuren.game.logic.scene.options;

import org.recast4j.detour.extras.Vector3f;

/**
 * @author xuren
 */
public class FindWayOption extends Option{
    private Vector3f point;

    public Vector3f getPoint() {
        return point;
    }

    public void setPoint(Vector3f point) {
        this.point = point;
    }
}
