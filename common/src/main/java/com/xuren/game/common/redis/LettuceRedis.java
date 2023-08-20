package com.xuren.game.common.redis;

import com.xuren.game.common.config.BaseConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.util.StringUtils;
import org.testng.collections.Maps;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LettuceRedis {
    private static final Map<String, RedisURI> URI_MAP = new HashMap<>();
    private static final Map<RedisURI, RedisClient> CLIENT_MAP = new HashMap<>();
    private static final Map<RedisURI, Map<RedisConnection, StatefulRedisConnection<String, String>>> CONNECTION_MAP = new ConcurrentHashMap<>();
//    private static final Map<String, RedisClient> clientMap = new HashMap<>();
//    private static final Map<String, StatefulRedisConnection<String, String>> connectMap = new HashMap<>();
//    private RedisClient client;
//    private StatefulRedisConnection<String, String> connection;
    private LettuceRedis() {

    }
    public static void init(String ip, int port, String username, String password) {
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(ip)
                .withPort(port)
                .withTimeout(Duration.ofSeconds(10));
        if (StringUtils.hasText(username)) {
            builder.withAuthentication(username, password.toCharArray());
        }

        RedisURI redisURI = builder.build();
        var client = RedisClient.create(redisURI);
        URI_MAP.put(BaseConfig.getInstance().getSec(), redisURI);
        CLIENT_MAP.put(redisURI, client);
    }

    public static RedisAsyncCommands<String, String> async(String sec) {
        var redisUri = URI_MAP.get(sec);
        return CONNECTION_MAP.computeIfAbsent(redisUri, redisURI -> {
            Map<RedisConnection, StatefulRedisConnection<String, String>> map = Maps.newConcurrentMap();
            map.computeIfAbsent(RedisConnection.Async, redisConnection -> CLIENT_MAP.get(URI_MAP.get(sec)).connect());
            return map;
        }).get(RedisConnection.Async).async();
    }

    public static RedisAsyncCommands<String, String> async() {
        return async(BaseConfig.getInstance().getSec());
    }

    public static RedisCommands<String, String> sync(String sec) {
        var redisUri = URI_MAP.get(sec);
        return CONNECTION_MAP.computeIfAbsent(redisUri, redisURI -> {
            Map<RedisConnection, StatefulRedisConnection<String, String>> map = Maps.newConcurrentMap();
            map.computeIfAbsent(RedisConnection.Sync, redisConnection -> CLIENT_MAP.get(URI_MAP.get(sec)).connect());
            return map;
        }).get(RedisConnection.Sync).sync();
    }

    public static RedisCommands<String, String> sync() {
        return sync(BaseConfig.getInstance().getSec());
    }

    private enum RedisConnection {
        Sync, Async
    }
}
