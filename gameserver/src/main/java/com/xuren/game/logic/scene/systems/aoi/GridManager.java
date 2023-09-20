package com.xuren.game.logic.scene.systems.aoi;

import com.beust.jcommander.internal.Sets;
import com.google.api.client.util.Lists;
import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetMsgCodecUtils;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.logic.scene.consts.SceneMsgConsts;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.syncmsg.AOIUpdateSyncMsg;
import com.xuren.game.logic.scene.syncmsg.LeaveSceneSyncMsg;
import com.xuren.game.logic.scene.syncmsg.SceneEnterSyncMsg;
import com.xuren.game.logic.scene.syncmsg.TransformSyncMsg;
import com.xuren.game.net.NetMsgSendUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.recast4j.detour.extras.Vector3f;
import org.testng.collections.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 世界坐标x,z都是正的
 */
public class GridManager {

    private float x;
    private float z;
    private final float blockSize;

    private final Map<Integer, Grid> grids;
    private int xLen;
    private int zLen;

    private Map<String, Integer> objGridIdMap;

    public GridManager(float x, float z, float blockSize) {
        this.x = x;
        this.z = z;
        this.blockSize = blockSize;

        xLen = (int) Math.ceil(x/blockSize);
        zLen = (int) Math.ceil(z/blockSize);
        grids = Maps.newHashMap();
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < zLen; j++) {
                grids.put(convertGridId(i, j), new Grid());
            }
        }
        objGridIdMap = Maps.newHashMap();
    }

    public static int convertGridId(int x, int z) {
        return x * 1000 + z;
    }

    public static int[] convertGridPos(int gridId) {
        int gridX = gridId / 1000;
        int gridZ = gridId % 1000;
        return new int[] {gridX, gridZ};
    }

    public void addObj(PlayerEntity playerEntity) {
        int gridId = nextGridId(playerEntity);
        Log.data.debug("player:{} enter gridId:{}", playerEntity.getRid(), gridId);
        Grid myGrid = getGrid(gridId);
        if (!objGridIdMap.containsKey(playerEntity.getRid())) {
            objGridIdMap.put(playerEntity.getRid(), gridId);
            myGrid.addPlayer(playerEntity);
        }
    }

    public void removeObj(PlayerEntity playerEntity) {
        if (objGridIdMap.containsKey(playerEntity.getRid())) {
            int gridId = objGridIdMap.get(playerEntity.getRid());
            Log.data.debug("player:{} out gridId:{}", playerEntity.getRid(), gridId);
            objGridIdMap.remove(playerEntity.getRid());
            getGrid(gridId).removePlayer(playerEntity);
        }
    }

    public void updateObj(PlayerEntity playerEntity) {
        // 1.判断当前所属格子是否和之前格子相同，不同需要更换格子
        int gridId = nextGridId(playerEntity);
        int oldGridId = objGridIdMap.get(playerEntity.getRid());
        if (objGridIdMap.containsKey(playerEntity.getRid()) && oldGridId != gridId) {
            Set<Integer> currGrids = currObserverGridIdList(playerEntity);
            Set<Integer> nextGrids = nextObserverGridIdList(playerEntity);
            var leaveGrids = CollectionUtils.subtract(currGrids, nextGrids);
            List<String> leaveRids = Lists.newArrayList();
            for(Integer leaveGridId : leaveGrids) {
                leaveRids.addAll(getGrid(leaveGridId).getPlayers().stream().map(PlayerEntity::getRid).collect(Collectors.toList()));
            }

            List<String> enterRids = Lists.newArrayList();
            List<PlayerEntity> enterPlayers = Lists.newArrayList();
            var enterGrids = CollectionUtils.subtract(nextGrids, currGrids);
            for(Integer enterGridId : enterGrids) {
                enterPlayers.addAll(getGrid(enterGridId).getPlayers());
                enterRids.addAll(getGrid(enterGridId).getPlayers().stream().map(PlayerEntity::getRid).collect(Collectors.toList()));
            }
            // 将角色从老格子移除
            removeObj(playerEntity);
            // 将角色加入新格子
            addObj(playerEntity);

            // 广播离开消息
            NetMsgSendUtils.broadcastLeaveMsg(leaveRids, playerEntity.getRid());

            //  广播进入消息
            NetMsgSendUtils.broadcastTransformMsg(enterRids, playerEntity);

            NetMsgSendUtils.sendAOIUpdateMsg(leaveRids, enterPlayers, playerEntity);
        }
    }

    public Grid nextGrid(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return getGrid(convertGridId(gridX, gridZ));
    }

    public int[] nextGridPos(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return new int[] {gridX, gridZ};
    }

    public int nextGridId(PlayerEntity playerEntity) {
        Vector3f pos = playerEntity.getTransformComponent().getPosition();
        int gridX = (int) Math.floor(pos.x / blockSize);
        int gridZ = (int) Math.floor(pos.z / blockSize);
        return convertGridId(gridX, gridZ);
    }

    public Grid getGrid(int x, int z) {
        return getGrid(convertGridId(x, z));
    }

    public Grid getGrid(int gridId) {
        return grids.get(gridId);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    /**
     * 角色没有发生移动才能调用，要不然获取的就不是之前位置的格子了
     * @param playerEntity
     * @return
     */
    public List<PlayerEntity> getCurrObserverPlayers(PlayerEntity playerEntity) {
        List<PlayerEntity> players = Lists.newArrayList();
        int[] gridPos = nextGridPos(playerEntity);
        if (gridPos[0] - 1 > 0) {
            if (gridPos[1] - 1 > 0) {
                players.addAll(getGrid(convertGridId(gridPos[0] - 1, gridPos[1] - 1)).getPlayers());
            }
            players.addAll(getGrid(convertGridId(gridPos[0] - 1, gridPos[1])).getPlayers());
            if (gridPos[1] + 1 < zLen) {
                players.addAll(getGrid(convertGridId(gridPos[0] - 1, gridPos[1] + 1)).getPlayers());
            }
        }
        if (gridPos[1] > 0) {
            players.addAll(getGrid(convertGridId(gridPos[0], gridPos[1] - 1)).getPlayers());
        }
        players.addAll(getGrid(convertGridId(gridPos[0], gridPos[1])).getPlayers());
        if (gridPos[1] + 1 < zLen) {
            players.addAll(getGrid(convertGridId(gridPos[0], gridPos[1] + 1)).getPlayers());
        }
        if (gridPos[0] + 1 < xLen) {
            if (gridPos[1] - 1 > 0) {
                players.addAll(getGrid(convertGridId(gridPos[0] + 1, gridPos[1] - 1)).getPlayers());
            }
            players.addAll(getGrid(convertGridId(gridPos[0] + 1, gridPos[1])).getPlayers());
            if (gridPos[1] + 1 < zLen) {
                players.addAll(getGrid(convertGridId(gridPos[0] + 1, gridPos[1] + 1)).getPlayers());
            }
        }
        return players;
    }

    public List<String> getCurrObserverPlayerIds(PlayerEntity playerEntity) {
        return getCurrObserverPlayers(playerEntity).stream().map(PlayerEntity::getRid).collect(Collectors.toList());
    }

    public Set<Integer> currObserverGridIdList(PlayerEntity playerEntity) {
        int[] gridPos = convertGridPos(objGridIdMap.get(playerEntity.getRid()));
        Set<Integer> set = Sets.newHashSet();
        if (gridPos[0] - 1 > 0) {
            if (gridPos[1] - 1 > 0) {
                set.add(convertGridId(gridPos[0] - 1, gridPos[1] - 1));
            }
            set.add(convertGridId(gridPos[0] - 1, gridPos[1]));
            if (gridPos[1] + 1 < zLen) {
                set.add(convertGridId(gridPos[0] - 1, gridPos[1] + 1));
            }
        }
        if (gridPos[1] > 0) {
            set.add(convertGridId(gridPos[0], gridPos[1] - 1));
        }
        set.add(convertGridId(gridPos[0], gridPos[1]));
        if (gridPos[1] + 1 < zLen) {
            set.add(convertGridId(gridPos[0], gridPos[1] + 1));
        }
        if (gridPos[0] + 1 < xLen) {
            if (gridPos[1] - 1 > 0) {
                set.add(convertGridId(gridPos[0] + 1, gridPos[1] - 1));
            }
            set.add(convertGridId(gridPos[0] + 1, gridPos[1]));
            if (gridPos[1] + 1 < zLen) {
                set.add(convertGridId(gridPos[0] + 1, gridPos[1] + 1));
            }
        }
        return set;
    }

    public Set<Integer> nextObserverGridIdList(PlayerEntity playerEntity) {
        int[] gridPos = convertGridPos(nextGridId(playerEntity));
        Set<Integer> set = Sets.newHashSet();
        if (gridPos[0] - 1 > 0) {
            if (gridPos[1] - 1 > 0) {
                set.add(convertGridId(gridPos[0] - 1, gridPos[1] - 1));
            }
            set.add(convertGridId(gridPos[0] - 1, gridPos[1]));
            if (gridPos[1] + 1 < zLen) {
                set.add(convertGridId(gridPos[0] - 1, gridPos[1] + 1));
            }
        }
        if (gridPos[1] > 0) {
            set.add(convertGridId(gridPos[0], gridPos[1] - 1));
        }
        set.add(convertGridId(gridPos[0], gridPos[1]));
        if (gridPos[1] + 1 < zLen) {
            set.add(convertGridId(gridPos[0], gridPos[1] + 1));
        }
        if (gridPos[0] + 1 < xLen) {
            if (gridPos[1] - 1 > 0) {
                set.add(convertGridId(gridPos[0] + 1, gridPos[1] - 1));
            }
            set.add(convertGridId(gridPos[0] + 1, gridPos[1]));
            if (gridPos[1] + 1 < zLen) {
                set.add(convertGridId(gridPos[0] + 1, gridPos[1] + 1));
            }
        }
        return set;
    }
}
