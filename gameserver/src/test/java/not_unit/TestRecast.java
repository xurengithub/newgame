package not_unit;

import com.xuren.game.logic.scene.nav.Easy3dNav;

import java.io.IOException;

/**
 * @author xuren
 */
public class TestRecast {
    public static void main(String[] args) throws IOException {
        Easy3dNav easy3dNav = new Easy3dNav();
        easy3dNav.setPrintMeshInfo(true);
        easy3dNav.init("dungeon.obj");
        //22.60652f, 10.197294f, -45.918674f
        System.out.println(easy3dNav.findHeight(new float[]{22.60652f, 0, -45.918674f}));
    }
}
