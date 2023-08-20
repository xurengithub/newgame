package not_unit;

import com.alibaba.fastjson.JSON;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.events.SceneEvent;
import com.xuren.game.logic.scene.options.FindWayOption;
import com.xuren.game.logic.scene.options.JoystickOption;
import com.xuren.game.logic.scene.options.OperationType;
import com.xuren.game.logic.scene.systems.aoi.GridManager;
import org.recast4j.detour.extras.Vector3f;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author xuren
 */
public class TestAOI {
    @Test
    public void test() throws IOException {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setRid("10001");
        playerEntity.setState(SceneState.IDLE);
        TransformComponent transformComponent = new TransformComponent(new Vector3f(100f, 0f, 1000f), 30, 10);
        playerEntity.setTransformComponent(transformComponent);

        GridManager gridManager = new GridManager(1000, 1000, 10);
        gridManager.addObj(playerEntity);
        System.out.println(JSON.toJSONString(playerEntity));
        gridManager.removeObj(playerEntity);
        System.out.println(JSON.toJSONString(playerEntity));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SceneManager.initScene();
        Scene scene = SceneManager.getScene("1");

        IntStream.range(0, 1000).forEach(i -> create(scene, String.valueOf(10000 + i)));

        Thread.sleep(20000);
    }

    private static void create(Scene scene, String rid) {
        String rid1 = rid;
        PlayerEntity p1 = createPlayer(rid1);
        scene.enter(p1);
        scene.addSceneEvent(createJoystickEvent(rid1, 45, 45, true));
    }

    private static PlayerEntity createPlayer(String rid) {
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setRid(rid);
        playerEntity.setState(SceneState.IDLE);
        TransformComponent transformComponent = new TransformComponent(new Vector3f(100f, 0f, 1000f), 30, 10);
        playerEntity.setTransformComponent(transformComponent);
        return playerEntity;
    }

    private static SceneEvent createFindWayEvent(String rid) {
        SceneEvent sceneEvent = new SceneEvent();
        sceneEvent.setRid("10001");
        FindWayOption option = new FindWayOption();
        option.setOperationType(OperationType.FIND_WAY.getType());
        option.setPoint(new Vector3f(6.4576626f, 10.197294f, -18.33406f));
        sceneEvent.setOperations(List.of(option));
        return sceneEvent;
    }

    private static SceneEvent createJoystickEvent(String rid, float cameraEulerY, float degree, boolean open) {
        SceneEvent sceneEvent = new SceneEvent();
        sceneEvent.setRid(rid);
        JoystickOption joystickOption = new JoystickOption();
        joystickOption.setOperationType(OperationType.JOYSTICK.getType());
        joystickOption.setCameraEulerY(cameraEulerY);
        joystickOption.setDegree(degree);
        joystickOption.setOpen(open);
        sceneEvent.setOperations(List.of(joystickOption));
        return sceneEvent;
    }
}
