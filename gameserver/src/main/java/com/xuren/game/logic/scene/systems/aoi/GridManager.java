package com.xuren.game.logic.scene.systems.aoi;

import com.xuren.game.logic.scene.entities.PlayerEntity;
import org.recast4j.detour.extras.Vector3f;

/**
 * 世界坐标x,z都是正的
 */
public class GridManager {

    private float x;
    private float z;
    private final float blockSize;

    private final Grid[][] grids;

    public GridManager(float x, float z, float blockSize) {
        this.x = x;
        this.z = z;
        this.blockSize = blockSize;

        int xLen = (int) Math.ceil(x/blockSize);
        int zLen = (int) Math.ceil(z/blockSize);
        grids = new Grid[xLen][zLen];
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < zLen; j++) {
                grids[i][j] = new Grid();
            }
        }
    }

    public void addObj(PlayerEntity playerEntity) {
        int[] gridPos = belongGridPos(playerEntity);
        Grid myGrid = getGrid(gridPos[0], gridPos[1]);
        playerEntity.setGridX(gridPos[0]);
        playerEntity.setGridZ(gridPos[1]);
        myGrid.addPlayer(playerEntity);
    }

    public void removeObj(PlayerEntity playerEntity) {
        playerEntity.setGridX(0);
        playerEntity.setGridZ(0);
        grids[playerEntity.getGridX()][playerEntity.getGridZ()].removePlayer(playerEntity);
    }

    public Grid belongGrid(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.ceil(pos.x / blockSize);
        int gridZ = (int) Math.ceil(pos.z / blockSize);
        return grids[gridX][gridZ];
    }

    public int[] belongGridPos(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.ceil(pos.x / blockSize);
        int gridZ = (int) Math.ceil(pos.z / blockSize);
        return new int[] {gridX, gridZ};
    }

    public Grid getGrid(int x, int z) {
        return grids[x][z];
    }
}
