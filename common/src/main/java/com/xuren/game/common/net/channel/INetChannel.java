package com.xuren.game.common.net.channel;

/**
 * @author xuren
 */
public interface INetChannel {
    void sendMsg(Object msg);

    boolean isAlive();

    void close();
}
