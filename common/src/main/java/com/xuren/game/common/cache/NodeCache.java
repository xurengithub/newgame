package com.xuren.game.common.cache;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.Node;
import com.xuren.game.common.ServerType;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.zk.ZKClient;
import com.xuren.game.common.zk.ZkConsts;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuren
 */
public abstract class NodeCache {
    private static Map<String, Node> nodeCache = Maps.newConcurrentMap();


    public static void init() {
        var curator = ZKClient.instance.getCuratorFramework();
        var cache = new PathChildrenCache(curator, ZkConsts.NODE_PATH, true);
        cache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            var eventType = pathChildrenCacheEvent.getType();
            var data = pathChildrenCacheEvent.getData();
            var path = data.getPath();
            var dataBytes = data.getData();
            Log.system.debug("zk event type:{} path:{} data:{}", eventType, path, new String(dataBytes));
            if (StringUtils.startsWith(path, ZkConsts.NODE_PATH)) {
                switch (eventType) {
                    case CHILD_ADDED:
                    case CHILD_UPDATED:
                        nodeCache.put(path, JSON.parseObject(dataBytes, Node.class));
                        break;
                    case CHILD_REMOVED:
                        nodeCache.remove(path);
                        break;
                    default:
                        break;
                }
            }
        });
        try {
            cache.start();
            Log.system.debug("zk cache stated.");
        } catch (Exception e) {
            Log.system.error("zk cache cannot start.", e);
            throw new RuntimeException(e);
        }
    }

    public static List<Node> globals() {
        return nodes(ServerType.GLOBAL);
    }

    public static List<Node> nodes(ServerType serverType) {
        return nodeCache.values().stream().filter(node -> node.getType().equals(serverType.name())).collect(Collectors.toList());
    }
}
