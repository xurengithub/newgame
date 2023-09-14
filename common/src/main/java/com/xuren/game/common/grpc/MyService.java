package com.xuren.game.common.grpc;

/**
 * @author xuren
 */
public class MyService implements IService{
    @Override
    public String say(String content) {
        return "say:" + content;
    }
}
