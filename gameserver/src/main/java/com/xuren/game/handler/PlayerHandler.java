package com.xuren.game.handler;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;
import com.xuren.game.common.redis.LettuceRedis;
import com.xuren.game.handler.params.LoginParams;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import org.recast4j.detour.extras.Vector3f;

import java.util.Collections;
import java.util.Map;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = 10001, desc = "登陆接口")
    public Map<String, String> login(PlayerEntity player, LoginParams prams) {
        Log.data.info("login params:{}", JSON.toJSONString(prams));
        if (player == null) {
            createPlayer();
        }
        return Collections.singletonMap("data", "ss");
    }

    private PlayerEntity createPlayer() {
        PlayerEntity player = new PlayerEntity();
        player.setSceneId("1");
        player.setRid("10001_1");

        TransformComponent transformComponent = new TransformComponent(new Vector3f(0, 30, 0), 30, 10);
        player.setTransformComponent(transformComponent);

        HealthComponent healthComponent = new HealthComponent(100, 100);
        player.setHealthComponent(healthComponent);
        return player;
    }
}
