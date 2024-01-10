package com.xuren.game.logic.scene.syncmsg;

import com.xuren.game.logic.scene.entities.PlayerEntity;

import java.util.List;

/**
 * @author xuren
 */
public class PlayerEnterGridMsg {
    private List<PlayerEntity> players;

    public PlayerEnterGridMsg(List<PlayerEntity> players) {
        this.players = players;
    }

    public List<PlayerEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEntity> players) {
        this.players = players;
    }
}
