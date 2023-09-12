package com.xuren.game.cache;

import com.github.benmanes.caffeine.cache.*;
import com.xuren.game.common.config.BaseConfig;
import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public abstract class PlayerCache {
    private static AsyncLoadingCache<String, PlayerEntity> instance;
    public static void init() {
        instance = Caffeine.newBuilder()
                .maximumSize(10)
                .removalListener((key, value, cause) -> {
                    if (value != null) {
                        MongodbService.getMongodbService(BaseConfig.getInstance().getSec()).getReactiveMongoTemplate()
                                .save(value)
                                .doOnError(throwable -> Log.data.error("PlayerCache remove save error", throwable))
                                .subscribe();
                    }
                })
                .evictionListener((key, value, cause) -> Log.data.info("PlayerCache eviction"))
                .buildAsync(new AsyncCacheLoader<>() {
                    @Override
                    public @NonNull CompletableFuture<PlayerEntity> asyncLoad(@NonNull String key, @NonNull Executor executor) {
                        Criteria criteria = new Criteria();
                        criteria.and("_id").is(key);
                        Query query = Query.query(criteria);
                        return MongodbService.getMongodbService(BaseConfig.getInstance().getSec())
                                .getReactiveMongoTemplate()
                                .findOne(query, PlayerEntity.class)
                                .toFuture()
                                .exceptionally(t -> {
                                    Log.data.error("async load player error", t);
                                    return null;
                                });
                    }
                });
    }

    public static CompletableFuture<PlayerEntity> getAsync(String rid) {
        return instance.get(rid);
    }

    public static void clear() {
        instance.synchronous().invalidateAll();
    }
}
