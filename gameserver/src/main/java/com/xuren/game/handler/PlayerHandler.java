package com.xuren.game.handler;

import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = 10001, desc = "登陆接口")
    public void login(String rid, CommonParams commonParams) {

    }
}
