package not_unit;

import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.SceneState;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.events.SceneEvent;
import com.xuren.game.logic.scene.options.FindWayOption;
import com.xuren.game.logic.scene.options.OptionType;
import org.recast4j.detour.extras.Vector3f;

import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        SceneManager.initScene();
        Scene scene = SceneManager.getScene("1");

        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setRid("10001");
        playerEntity.setState(SceneState.IDLE);

//        new float[]{ 22.60652f, 10.197294f, -45.918674f }, new float[]{ 6.4576626f, 10.197294f, -18.33406f };
        TransformComponent transformComponent = new TransformComponent(new Vector3f(22.60652f, 10.197294f, -45.918674f), 30, 10);
        playerEntity.setTransformComponent(transformComponent);
        scene.enter(playerEntity);

        Thread.sleep(1000);

        SceneEvent sceneEvent = new SceneEvent();
        sceneEvent.setRid("10001");
        FindWayOption option = new FindWayOption();
        option.setOptionType(OptionType.FIND_WAY.getType());
        option.setPoint(new Vector3f(6.4576626f, 10.197294f, -18.33406f));
        sceneEvent.setOptions(List.of(option));
        scene.addSceneEvent(sceneEvent);
        Thread.sleep(100000);
    }
}
