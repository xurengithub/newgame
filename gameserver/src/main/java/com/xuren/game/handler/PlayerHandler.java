package com.xuren.game.handler;

import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = 10001, desc = "dddd")
    public void login() {

    }
}
