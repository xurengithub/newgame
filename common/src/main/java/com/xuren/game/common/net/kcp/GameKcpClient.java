package com.xuren.game.common.net.kcp;

import io.netty.buffer.ByteBuf;
import org.beykery.jkcp.KcpClient;
import org.beykery.jkcp.KcpOnUdp;

public class GameKcpClient extends KcpClient {
    @Override
    public void handleReceive(ByteBuf byteBuf, KcpOnUdp kcpOnUdp) {

    }

    @Override
    public void handleException(Throwable throwable, KcpOnUdp kcpOnUdp) {

    }
}
