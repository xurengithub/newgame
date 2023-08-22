package com.xuren.game.logic.scene.syncmsg;

import com.xuren.game.logic.scene.components.TransformComponent;

import java.util.List;

/**
 * @author xuren
 */
public class SceneEnterSyncMsg {
    // 场景id
    private String sceneId;
    // 我的场景内信息
    private TransformComponent myPlayer;
    // 其他人信息
    private List<TransformComponent> otherPlayers;

    public SceneEnterSyncMsg(String sceneId, TransformComponent myPlayer, List<TransformComponent> otherPlayers) {
        this.sceneId = sceneId;
        this.myPlayer = myPlayer;
        this.otherPlayers = otherPlayers;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public TransformComponent getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(TransformComponent myPlayer) {
        this.myPlayer = myPlayer;
    }

    public List<TransformComponent> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(List<TransformComponent> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }
}
