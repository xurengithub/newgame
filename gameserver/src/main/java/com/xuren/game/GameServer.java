package com.xuren.game;

import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.tcp.NettyTcpServer;
import com.xuren.game.common.zk.ZKClient;
import com.xuren.game.common.zk.ZKConfig;
import com.xuren.game.logic.scene.SceneManager;

import java.io.IOException;


/**
 * @author xuren
 */
public class GameServer {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Log.data.error("defaultUncaughtExceptionHandler threadName:{} error", t.getName(), e));
        initProperties();
        initZK();
        initScene();
        initNet();
    }

    private static void initScene() {
        try {
            SceneManager.initScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initNet() {
        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.bind("127.0.0.1", 55667);
        nettyTcpServer.start();
    }

    private static void initZK() {
        ZKConfig zkConfig = new ZKConfig();
        ZKClient.init(zkConfig);
    }

    private static void initProperties() {

    }

}
