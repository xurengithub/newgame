package com.xuren.game.common.db.mongo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.testng.collections.Maps;

import java.util.Map;

import static org.springframework.data.mongodb.core.ReactiveMongoTemplate.NO_OP_REF_RESOLVER;

/**
 * @author xuren
 */
public class MongodbService implements AutoCloseable{
    private final MongoClient client;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private static final Map<String, MongodbService> INSTANCES = Maps.newConcurrentMap();

    private MongodbService(String connectStr, String dbName, String username, String password) {
        this.client = MongoClients.create(connectStr);
        ReactiveMongoDatabaseFactory factory = new SimpleReactiveMongoDatabaseFactory(client, dbName);

        MongoMappingContext context = new MongoMappingContext();
        context.setAutoIndexCreation(true);
        context.afterPropertiesSet();

        MappingMongoConverter mongoConverter = new MappingMongoConverter(NO_OP_REF_RESOLVER, context);
        mongoConverter.setCodecRegistryProvider(factory);
        mongoConverter.afterPropertiesSet();
        this.reactiveMongoTemplate = new ReactiveMongoTemplate(factory, mongoConverter);
    }
    public static void init(String connectStr, String dbName, String userName, String password, String sec) {
        INSTANCES.put(sec, new MongodbService(connectStr, dbName, userName, password));
    }

    @Override
    public void close() throws Exception {
        client.close();
    }

    public static MongodbService getMongodbService(String key) {
        return INSTANCES.get(key);
    }

    public ReactiveMongoTemplate getReactiveMongoTemplate() {
        return reactiveMongoTemplate;
    }
}
