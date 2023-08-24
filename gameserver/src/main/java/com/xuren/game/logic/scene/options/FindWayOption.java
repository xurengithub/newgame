package com.xuren.game.logic.scene.options;

import org.recast4j.detour.extras.Vector3f;

/**
 * @author xuren
 */
@OperationAnnotation(type = OperationType.FIND_WAY)
public class FindWayOption extends Operation{
    private Vector3f point;

    public Vector3f getPoint() {
        return point;
    }

    public void setPoint(Vector3f point) {
        this.point = point;
    }
}
