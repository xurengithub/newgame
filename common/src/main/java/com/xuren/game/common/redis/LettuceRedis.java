package com.xuren.game.common.redis;

import com.xuren.game.common.config.BaseConfig;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LettuceRedis implements AutoCloseable{
    private static final Map<String, LettuceRedis> instanceMap = new HashMap<>();
    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;
    private LettuceRedis() {

    }
    public static void init(String ip, int port, String username, String password) {
        LettuceRedis lettuceRedis = new LettuceRedis();
        RedisURI.Builder builder = RedisURI.builder()
                .withHost(ip)
                .withPort(port)
                .withTimeout(Duration.ofSeconds(10));
        if (StringUtils.hasText(username)) {
            builder.withAuthentication(username, password.toCharArray());
        }
        RedisURI redisURI = builder.build();
        lettuceRedis.client = RedisClient.create(redisURI);
        lettuceRedis.connection = lettuceRedis.client.connect();
        instanceMap.put(BaseConfig.getInstance().getSec(), lettuceRedis);
    }

    public static void closeAll() {
        instanceMap.values()
                .forEach(lettuceRedis -> {
                    try {
                        lettuceRedis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public static LettuceRedis get(String sec) {
        return instanceMap.get(sec);
    }

    @Override
    public void close() {
        connection.close();
        client.shutdown();
    }
}
