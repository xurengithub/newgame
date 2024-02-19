package com.xuren.game.handler;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;
import com.xuren.game.consts.MsgCodeConsts;
import com.xuren.game.handler.params.EmptyParams;
import com.xuren.game.handler.params.LoginParams;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.proto.MsgLogin;
import com.xuren.game.service.PlayerService;

import java.util.concurrent.CompletionStage;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = MsgCodeConsts.LOGIN, desc = "登陆接口")
    public CompletionStage<MsgLogin> login(PlayerEntity player, LoginParams params) {
        Log.data.info("login params:{}", JSON.toJSONString(params));
        return PlayerService.login(player, params);
    }

    @Interface(code = MsgCodeConsts.LOGOUT, desc = "断开")
    public void logout(PlayerEntity player, EmptyParams params) {
        PlayerService.logout(player);
    }
}
