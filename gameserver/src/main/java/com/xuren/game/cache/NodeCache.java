package com.xuren.game.cache;

import com.alibaba.fastjson.JSON;
import com.xuren.game.common.Node;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.zk.ZKClient;
import com.xuren.game.common.zk.ZkConsts;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.testng.collections.Maps;

import java.util.Map;

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
        }
    }
}
