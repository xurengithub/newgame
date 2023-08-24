package com.xuren.game.handler;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.proto.MsgBase;
import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;
import com.xuren.game.common.redis.LettuceRedis;
import com.xuren.game.consts.MsgCodeConsts;
import com.xuren.game.handler.params.LoginParams;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.proto.MsgLogin;
import com.xuren.game.proto.PlayerSimpleInfo;
import org.recast4j.detour.extras.Vector3f;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = MsgCodeConsts.LOGIN, desc = "登陆接口")
    public CompletionStage<MsgLogin> login(PlayerEntity player, LoginParams params) {
        Log.data.info("login params:{}", JSON.toJSONString(params));
        MsgLogin msgLogin = new MsgLogin();
        msgLogin.protoName = msgLogin.getClass().getSimpleName();
        msgLogin.setResult(0);
        if (player == null) {
            return MongodbService.getMongodbService()
                    .getReactiveMongoTemplate()
                    .insert(createPlayer(params.rid))
                    .toFuture()
                    .thenApply(playerEntity -> {
                        SceneManager.initInScene(playerEntity);
                        return msgLogin;
                    });
        }
        SceneManager.initInScene(player);
//        PlayerSimpleInfo[] playerSimpleInfos = new PlayerSimpleInfo[1];
//        msgLogin.setPlayerSimpleInfos();
        return CompletableFuture.completedStage(msgLogin);
    }

    private PlayerEntity createPlayer(String rid) {
        PlayerEntity player = new PlayerEntity();
        player.setSceneId("");
        player.setRid(rid);

        TransformComponent transformComponent = new TransformComponent(new Vector3f(0, 30, 0), 30, 5);
        player.setTransformComponent(transformComponent);

        HealthComponent healthComponent = new HealthComponent(100, 100);
        player.setHealthComponent(healthComponent);
        return player;
    }
}
