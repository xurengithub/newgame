package com.xuren.game.logic.scene.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author xuren
 */
@Document("roleScene")
public class PlayerSceneEntity {
    @Indexed(unique = true, name = "rid")
    private String rid;
}
