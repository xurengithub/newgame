package com.xuren.game.common.db.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.xuren.game.common.config.BaseConfig;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.util.StringUtils;
import org.testng.collections.Maps;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.ReactiveMongoTemplate.NO_OP_REF_RESOLVER;

/**
 * @author xuren
 */
public class MongodbService implements AutoCloseable{
    private final MongoClient client;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private static final Map<String, MongodbService> INSTANCES = Maps.newConcurrentMap();

    private MongodbService(MongoConfig mongoConfig) {
        var settings = MongoClientSettings.builder()
            .readPreference(ReadPreference.primaryPreferred())
            .applyToClusterSettings(cluster -> {
                    cluster.hosts(mongoConfig.getCluster()
                        .stream()
                        .map(host -> new ServerAddress(host.getHost(), host.getPort()))
                        .collect(Collectors.toList())
                    );
                    if (StringUtils.hasText(mongoConfig.getReplicaSetName())) {
                        cluster.requiredReplicaSetName(mongoConfig.getReplicaSetName());
                    }
                }
            )
            .applicationName("mmo")
            .credential(MongoCredential.createCredential(
                mongoConfig.getUsername(),
                mongoConfig.getAuthDbName(),
                mongoConfig.getPassword().toCharArray()
            ))
            .applyToConnectionPoolSettings(pool -> pool.maxSize(mongoConfig.getMaxConnectionPoolSize()))
            .build();

        this.client = MongoClients.create(settings);
        ReactiveMongoDatabaseFactory factory = new SimpleReactiveMongoDatabaseFactory(client, mongoConfig.getDbName());

        MongoMappingContext context = new MongoMappingContext();
        context.setAutoIndexCreation(true);
        context.afterPropertiesSet();

        MappingMongoConverter mongoConverter = new MappingMongoConverter(NO_OP_REF_RESOLVER, context);
        mongoConverter.setCodecRegistryProvider(factory);
        mongoConverter.afterPropertiesSet();
        this.reactiveMongoTemplate = new ReactiveMongoTemplate(factory, mongoConverter);
    }
    public static void init(MongoConfig mongoConfig, String sec) {
        INSTANCES.put(sec, new MongodbService(mongoConfig));
    }

    @Override
    public void close() throws Exception {
        client.close();
    }

    public static MongodbService getMongodbService(String key) {
        return INSTANCES.get(key);
    }

    public static MongodbService getMongodbService() {
        return getMongodbService(BaseConfig.getInstance().getSec());
    }

    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }
}
