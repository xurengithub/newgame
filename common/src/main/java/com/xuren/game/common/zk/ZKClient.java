package com.xuren.game.common.zk;

import com.xuren.game.common.utils.ZKUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.List;

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

    public static void registerNode() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String rootPath = "/" + ZkConsts.GAME_NODE_PATH;
            if (instance.existsPath(rootPath)) {
                instance.create(rootPath, "".getBytes());
            }
            instance.createEphemeral(rootPath + "/" + ip, "".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(final String path, final byte[] payload) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded().forPath(path, payload);
    }

    public void createEphemeral(final String path, final byte[] payload) throws Exception {
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
    }

    public String createEphemeralSequential(final String path, final byte[] payload) throws Exception {
        return curatorFramework.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, payload);
    }

    public void setData(final String path, final byte[] payload) throws Exception {
        curatorFramework.setData().forPath(path, payload);
    }

    public void delete(final String path) throws Exception {
        curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);

    }

    public void guaranteedDelete(final String path) throws Exception {
        curatorFramework.delete().guaranteed().forPath(path);
    }

    public String getData(final String path) throws Exception {
        return new String(curatorFramework.getData().forPath(path));
    }

    public List<String> getChildren(final String path) throws Exception {
        return curatorFramework.getChildren().forPath(path);
    }

    public boolean existsPath(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path) != null;
    }
}
