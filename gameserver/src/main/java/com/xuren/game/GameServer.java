package com.xuren.game;

import com.xuren.game.common.config.BaseConfig;
import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.tcp.server.NettyTcpServer;
import com.xuren.game.common.proto.ProtoHandlerManager;
import com.xuren.game.common.redis.LettuceRedis;
import com.xuren.game.common.zk.ZKClient;
import com.xuren.game.common.zk.ZKConfig;
import com.xuren.game.cache.PlayerCache;
import com.xuren.game.logic.scene.SceneManager;

import java.io.IOException;


/**
 * @author xuren
 */
public class GameServer {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Log.data.error("defaultUncaughtExceptionHandler threadName:{} error", t.getName(), e));
        initProtoHandler();
        initZK();
        initMongo();
        initCache();
        initRedis();
        initProperties();
//        initScene();
        initNet();
    }

    private static void initRedis() {
        LettuceRedis.init("127.0.0.1", 3306, "", "");
    }

    private static void initCache() {
        PlayerCache.init();
    }

    private static void initMongo() {
        MongodbService.init("mongodb://127.0.0.1:27017", "1", "1");
    }

    /**
     * 初始化网络
     */
    private static void initNet() {
        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.bind("127.0.0.1", 55667);
        nettyTcpServer.start();
    }

    /**
     * 初始化协议处理器
     */
    private static void initProtoHandler() {
        ProtoHandlerManager.init("com.xuren.game.handler");
    }

    /**
     * 初始化场景
     */
    private static void initScene() {
        try {
            SceneManager.initScene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initZK() {
        ZKClient.init(ZKConfig.instance);
        ZKClient.registerNode();
    }

    private static void initProperties() {
        BaseConfig.getInstance().setSec("1");
//        ZKConfig.instance;
        // todo 初始化属性系统
        // 希望先加载.properties文件
        // 然后程序—D的属性
        // zk
        // zk > -D > .properties
    }

}
