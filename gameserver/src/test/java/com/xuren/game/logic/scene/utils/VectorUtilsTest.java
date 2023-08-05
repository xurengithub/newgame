package com.xuren.game.logic.scene.utils;

import org.recast4j.detour.extras.Vector3f;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class VectorUtilsTest {

    @Test
    public void testDistance() {
        Vector3f vector3f = new Vector3f();
        vector3f.x = 1;
        vector3f.y = 1;
        vector3f.z = 1;
        Vector3f vector3f2 = new Vector3f();
        vector3f2.x = 0;
        vector3f2.y = 0;
        vector3f2.z = 0;

        System.out.println(VectorUtils.distance(vector3f, vector3f2));
    }
}