package not_unit;

import com.xuren.game.common.net.tcp.server.NettyTcpServer;

/**
 * @author xuren
 */
public class TestServer {
    public static void main(String[] args) {
        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.bind("127.0.0.1", 55667);
        nettyTcpServer.start();
    }
}
