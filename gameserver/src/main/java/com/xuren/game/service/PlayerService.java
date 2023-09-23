package com.xuren.game.service;

import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.handler.params.LoginParams;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.components.HealthComponent;
import com.xuren.game.logic.scene.components.TransformComponent;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.proto.MsgLogin;
import org.recast4j.detour.extras.Vector3f;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author xuren
 */
public class PlayerService {
    public static CompletionStage<MsgLogin> login(PlayerEntity player, LoginParams params) {
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

    private static PlayerEntity createPlayer(String rid) {
        PlayerEntity player = new PlayerEntity();
        player.setSceneId("");
        player.setRid(rid);

        TransformComponent transformComponent = new TransformComponent(new Vector3f(0, 30, 0), 30, 5);
        player.setTransformComponent(transformComponent);

        HealthComponent healthComponent = new HealthComponent(100, 100);
        player.setHealthComponent(healthComponent);
        return player;
    }

    public static void logout(PlayerEntity player) {
        SceneManager.leave(player);
    }
}
