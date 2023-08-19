package com.xuren.game.common.zk;

import com.xuren.game.common.utils.ZKUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuren
 */
public class ZKClient {
    public static ZKClient instance = new ZKClient();
    private CuratorFramework curatorFramework;
    public static void init(ZKConfig zkConfig) {
        instance.curatorFramework = CuratorFrameworkFactory.builder()
            .connectString(zkConfig.getConnectString())
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .connectionTimeoutMs(zkConfig.getConnectionTimeoutMs())
            .sessionTimeoutMs(zkConfig.getSessionTimeoutMs())
            .namespace(zkConfig.getNamespace())
            .build();
        registerNode();
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }

    public static void registerNode() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();

            if (instance.curatorFramework.checkExists().forPath("/" + ZkConsts.GAME_NODE_PATH) == null) {
                ZKUtils.create(instance.curatorFramework, "/" + ZkConsts.GAME_NODE_PATH, "".getBytes());
            }
            ZKUtils.createEphemeral(instance.curatorFramework, "/" + ZkConsts.GAME_NODE_PATH + "/" + ip, "".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
