package com.xuren.game.logic.scene.entities;

import com.xuren.game.logic.scene.components.TransformComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xuren
 */
@Document("monster")
public class MonsterEntity {
    @Id
    private String id;
    private TransformComponent transformComponent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    public void setTransformComponent(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
    }
}
