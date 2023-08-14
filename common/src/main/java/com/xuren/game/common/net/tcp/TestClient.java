package com.xuren.game.common.net.tcp;

import com.xuren.game.common.proto.MsgBase;

/**
 * @author xuren
 */
public class TestClient {
    public static void main(String[] args) {
        NettyTcpClient nettyTcpClient = new NettyTcpClient();
        nettyTcpClient.conect("127.0.0.1", 55667);

        MsgBase msgBase = new MsgBase();
        msgBase.setProtoName("login");
        nettyTcpClient.send(msgBase);
    }
}
