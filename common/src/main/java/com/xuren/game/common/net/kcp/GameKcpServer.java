package com.xuren.game.common.net.kcp;

import io.netty.buffer.ByteBuf;
import org.beykery.jkcp.KcpOnUdp;
import org.beykery.jkcp.KcpServer;

public class GameKcpServer extends KcpServer {
    public GameKcpServer(int port, int workerSize) {
        super(port, workerSize);
    }

    @Override
    public void handleReceive(ByteBuf byteBuf, KcpOnUdp kcpOnUdp) {

    }

    @Override
    public void handleException(Throwable throwable, KcpOnUdp kcpOnUdp) {

    }

    @Override
    public void handleClose(KcpOnUdp kcpOnUdp) {

    }
}
