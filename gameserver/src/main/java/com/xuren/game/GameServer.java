package com.xuren.game;

import com.xuren.game.common.config.BaseConfig;
import com.xuren.game.common.config.HostConfig;
import com.xuren.game.common.db.mongo.MongoConfig;
import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.tcp.server.NettyTcpServer;
import com.xuren.game.common.proto.ProtoHandlerManager;
import com.xuren.game.common.redis.LettuceRedis;
import com.xuren.game.common.zk.ZKClient;
import com.xuren.game.common.zk.ZKConfig;
import com.xuren.game.cache.PlayerCache;
import com.xuren.game.logic.scene.SceneManager;
import com.xuren.game.logic.scene.options.OperationManager;
import com.xuren.game.net.NettyTcpServerInitializer;

import java.io.IOException;
import java.util.List;


/**
 * @author xuren
 */
public class GameServer {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Log.data.error("defaultUncaughtExceptionHandler threadName:{} error", t.getName(), e));
        initProperties();
        OperationManager.init("com.xuren.game.logic.scene.options");
        initProtoHandler();
        initZK();
        initMongo();
        initCache();
        initRedis();
        initNet();
//        initScene();

    }

    private static void initRedis() {
        LettuceRedis.init("127.0.0.1", 6379, "", "");
    }

    private static void initCache() {
        PlayerCache.init();
    }

    private static void initMongo() {
        MongodbService.init(MongoConfig.instance, BaseConfig.getInstance().getSec());
    }

    /**
     * 初始化网络
     */
    private static void initNet() {
        NettyTcpServer nettyTcpServer = new NettyTcpServer();
        nettyTcpServer.init(new NettyTcpServerInitializer());
        nettyTcpServer.bind("127.0.0.1", BaseConfig.getInstance().getNetPort());
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
        ZKClient.start();
        ZKClient.registerNode();
    }

    private static void initProperties() {
        BaseConfig.getInstance().setSec("1");

        ZKConfig.instance.setSessionTimeoutMs(60000);
        ZKConfig.instance.setConnectionTimeoutMs(5000);
        ZKConfig.instance.setConnectString("127.0.0.1:2181");
        ZKConfig.instance.setElapsedTimeMs(5000);
        ZKConfig.instance.setNamespace("game");
        ZKConfig.instance.setRetryCount(5);
        ZKConfig.instance.setRetrySleepTimeMs(1000);

        MongoConfig.instance.setAuthDbName("admin");
        MongoConfig.instance.setReplicaSetName(null);
        MongoConfig.instance.setMaxConnectionPoolSize(10);
        MongoConfig.instance.setPassword("123456");
        MongoConfig.instance.setUsername("root");
        MongoConfig.instance.setDbName(BaseConfig.getInstance().getSec());
        MongoConfig.instance.setCluster(List.of(new HostConfig("127.0.0.1", 27017)));
        // todo 初始化属性系统
        // 希望先加载.properties文件
        // 然后程序—D的属性
        // zk
        // zk > -D > .properties
    }

}
