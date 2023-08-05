package com.xuren.game.logic.scene.nav;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author xuren
 */
public class MyEasy3dNavTest extends TestCase {

    @Test
    public void testInit() throws IOException {
        MyEasy3dNav myNav = new MyEasy3dNav();
        myNav.setPrintMeshInfo(true);
        myNav.init("dungeon.obj");
        List<float[]> list = myNav.find(new float[]{ 22.60652f, 10.197294f, -45.918674f }, new float[]{ 6.4576626f, 10.197294f, -18.33406f });
        System.out.println(JSON.toJSONString(list));
    }
}