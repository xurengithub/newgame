package com.xuren.game.logic.scene.systems.aoi;

import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import org.recast4j.detour.extras.Vector3f;

import java.util.List;

/**
 * 世界坐标x,z都是正的
 */
public class GridManager {

    private float x;
    private float z;
    private final float blockSize;

    private final Grid[][] grids;
    private int xLen;
    private int zLen;

    public GridManager(float x, float z, float blockSize) {
        this.x = x;
        this.z = z;
        this.blockSize = blockSize;

        xLen = (int) Math.ceil(x/blockSize);
        zLen = (int) Math.ceil(z/blockSize);
        grids = new Grid[xLen][zLen];
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < zLen; j++) {
                grids[i][j] = new Grid();
            }
        }
    }

    public void addObj(PlayerEntity playerEntity) {
        int[] gridPos = belongGridPos(playerEntity);
        Log.data.debug("player:{} enter grid {}:{}", playerEntity.getRid(), gridPos[0], gridPos[1]);
        Grid myGrid = getGrid(gridPos[0], gridPos[1]);
        playerEntity.setGridX(gridPos[0]);
        playerEntity.setGridZ(gridPos[1]);
        myGrid.addPlayer(playerEntity);
    }

    public void removeObj(PlayerEntity playerEntity) {
        Log.data.debug("player:{} out grid {}:{}", playerEntity.getRid(), playerEntity.getGridX(), playerEntity.getGridZ());
        playerEntity.setGridX(0);
        playerEntity.setGridZ(0);
        grids[playerEntity.getGridX()][playerEntity.getGridZ()].removePlayer(playerEntity);
    }

    public Grid belongGrid(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return grids[gridX][gridZ];
    }

    public int[] belongGridPos(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return new int[] {gridX, gridZ};
    }

    public Grid getGrid(int x, int z) {
        return grids[x][z];
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public List<PlayerEntity> getObserverPlayers(PlayerEntity playerEntity) {
        List<PlayerEntity> players = Lists.newArrayList();
        int[] gridPos = belongGridPos(playerEntity);
        if (gridPos[0] - 1 > 0) {
            if (gridPos[1] - 1 > 0) {
                players.addAll(grids[gridPos[0] - 1][gridPos[1] - 1].getPlayers());
            }
            players.addAll(grids[gridPos[0] - 1][gridPos[1]].getPlayers());
            if (gridPos[1] + 1 < zLen) {
                players.addAll(grids[gridPos[0] - 1][gridPos[1] + 1].getPlayers());
            }
        }
        if (gridPos[1] > 0) {
            players.addAll(grids[gridPos[0]][gridPos[1] - 1].getPlayers());
        }
        players.addAll(grids[gridPos[0]][gridPos[1]].getPlayers());
        if (gridPos[1] + 1 < zLen) {
            players.addAll(grids[gridPos[0]][gridPos[1] + 1].getPlayers());
        }
        if (gridPos[0] + 1 < xLen) {
            if (gridPos[1] - 1 > 0) {
                players.addAll(grids[gridPos[0] + 1][gridPos[1] - 1].getPlayers());
            }
            players.addAll(grids[gridPos[0] + 1][gridPos[1]].getPlayers());
            if (gridPos[1] + 1 < zLen) {
                players.addAll(grids[gridPos[0] + 1][gridPos[1] + 1].getPlayers());
            }
        }
        return players;
    }
}
