package com.xuren.game.handler;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.proto.ProtoHandler;
import com.xuren.game.common.proto.Interface;
import com.xuren.game.handler.params.LoginParams;

import java.util.Collections;
import java.util.Map;

/**
 * @author xuren
 */
@ProtoHandler(module = 10000)
public class PlayerHandler {
    @Interface(code = 10001, desc = "登陆接口")
    public Map<String, String> login(String rid, LoginParams prams) {
        Log.data.info("login params:{}", JSON.toJSONString(prams));
        return Collections.singletonMap("data", "ss");
    }
}
