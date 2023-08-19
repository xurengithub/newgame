package not_unit;

import com.alibaba.fastjson.JSON;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.xuren.game.common.db.mongo.MongodbService;
import com.xuren.game.model.Role;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.util.concurrent.ExecutionException;

/**
 * @author xuren
 */
public class TestMongo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MongodbService.init("mongodb://127.0.0.1:27017", "1", "1");
        Role role = new Role();
        role.setRid("10005  ");
        System.out.println(JSON.toJSONString(MongodbService.getMongodbService("1").getReactiveMongoTemplate().insert(role).block()));;

//        ReactiveMongoTemplate reactiveMongoTemplate = new ReactiveMongoTemplate(MongoClients.create("mongodb://127.0.0.1:27017"), "1");
//        System.out.println(JSON.toJSONString(reactiveMongoTemplate.insert(role).block()));;

    }
}
