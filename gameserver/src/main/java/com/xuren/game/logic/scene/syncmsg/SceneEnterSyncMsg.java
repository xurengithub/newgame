package com.xuren.game.logic.scene.syncmsg;

import com.xuren.game.logic.scene.entities.PlayerEntity;

import java.util.List;

/**
 * @author xuren
 */
public class SceneEnterSyncMsg {
    private String playerId;
    // 场景id
    private String sceneId;
    // 我的场景内信息
    private PlayerEntity myPlayer;
    // 其他人信息
    private List<PlayerEntity> otherPlayers;

    public SceneEnterSyncMsg(String playerId, String sceneId, PlayerEntity myPlayer, List<PlayerEntity> otherPlayers) {
        this.playerId = playerId;
        this.sceneId = sceneId;
        this.myPlayer = myPlayer;
        this.otherPlayers = otherPlayers;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public PlayerEntity getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(PlayerEntity myPlayer) {
        this.myPlayer = myPlayer;
    }

    public List<PlayerEntity> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(List<PlayerEntity> otherPlayers) {
        this.otherPlayers = otherPlayers;
    }
}
