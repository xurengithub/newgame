package com.xuren.game.logic.scene.systems.aoi;

import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import org.recast4j.detour.extras.Vector3f;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;

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

    private Map<String, int[]> objPosMap;

    private Map<String, Integer> objGridIdMap;

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
        objPosMap = Maps.newHashMap();
        objGridIdMap = Maps.newHashMap();
    }

    public void addObj(PlayerEntity playerEntity) {
        int[] gridPos = belongGridPos(playerEntity);
        Log.data.debug("player:{} enter grid {}:{}", playerEntity.getRid(), gridPos[0], gridPos[1]);
        Grid myGrid = getGrid(gridPos[0], gridPos[1]);
        if (!objPosMap.containsKey(playerEntity.getRid())) {
            objPosMap.put(playerEntity.getRid(), gridPos);
            myGrid.addPlayer(playerEntity);
            // todo notify 角色进入了场景，通知其他角色
        }
//        if (!objPosMap.containsKey(playerEntity.getRid())) {
//            objPosMap.put(playerEntity.getRid(), gridPos);
//            myGrid.addPlayer(playerEntity);
//            // todo notify 角色进入了场景，通知其他角色
//        }
    }

    public void removeObj(PlayerEntity playerEntity) {
        if (objPosMap.containsKey(playerEntity.getRid())) {
            int[] gridPos = objPosMap.get(playerEntity.getRid());
            Log.data.debug("player:{} out grid {}:{}", playerEntity.getRid(), gridPos[0], gridPos[1]);
            objPosMap.remove(playerEntity.getRid());
            grids[gridPos[0]][gridPos[1]].removePlayer(playerEntity);
            // todo notify
        }
    }

    public void updateObj(PlayerEntity playerEntity) {
        // 1.判断当前所属格子是否和之前格子相同，不同需要更换格子
        int[] gridPos = belongGridPos(playerEntity);
        int[] oldGridPos = objPosMap.get(playerEntity.getRid());
        if (objPosMap.containsKey(playerEntity.getRid()) && (oldGridPos[0] != gridPos[0] || oldGridPos[1] != gridPos[1])) {
            // 将角色从老格子移除
            removeObj(playerEntity);
            // 将角色加入新格子
            addObj(playerEntity);
        }
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

    public int belongGridId(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return gridX * 1000 + gridZ;
    }

    public Grid getGrid(int x, int z) {
        return grids[x][z];
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
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
