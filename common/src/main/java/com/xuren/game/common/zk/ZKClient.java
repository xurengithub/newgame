package com.xuren.game.common.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

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
    }

    public CuratorFramework getCuratorFramework() {
        return curatorFramework;
    }
}
