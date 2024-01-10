package com.xuren.game.net;

import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.consts.SceneMsgConsts;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.syncmsg.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuren
 */
public abstract class NetMsgSendUtils {
    public static void broadcast(List<String> rids, NetMsg netMsg) {
        for (var rid : rids) {
            sendMsg(rid, netMsg);
        }
    }

    public static void sendMsg(String rid, NetMsg netMsg) {
        try {
            var netChannel = OnlineNetChannels.getChannel(rid);
            if (netChannel != null && netChannel.isAlive()) {
                netChannel.sendMsg(netMsg);
            } else {
                Log.system.warn("player:{} channel is not active", rid);
            }
        } catch (Exception e) {
            Log.system.error("sendMsg error, rid:{}", rid, e);
        }
    }

    /**
     * 广播位置信息
     * @param scene
     * @param playerEntity
     */
    public static void broadcastTransformSyncMsg2Interesting(Scene scene, PlayerEntity playerEntity) {
        List<String> observerPlayers = scene.getGridManager().getCurrObserverPlayerIds(playerEntity);
        broadcastTransformMsg(observerPlayers, playerEntity);
    }

    public static void broadcastTransformMsg(List<String> toRids, PlayerEntity playerEntity) {
        TransformSyncMsg msg = new TransformSyncMsg(playerEntity.getRid(), playerEntity.getTransformComponent());
        NetMsg enterMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.TRANSFORM_SYNC, -1, msg, System.currentTimeMillis());
        broadcast(toRids, enterMsg);
    }

    public static void broadcastLeaveGridMsg(List<String> toRids, List<String> rids) {
        PlayersLeaveGridMsg playersLeaveGridMsg = new PlayersLeaveGridMsg(rids);
        NetMsg leaveMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.LEAVE_GRID_SYNC, -1, playersLeaveGridMsg, System.currentTimeMillis());
        broadcast(toRids, leaveMsg);
    }

    public static void sendEnterSceneMsg(Scene scene, PlayerEntity playerEntity) {
        SceneEnterSyncMsg sceneEnterSyncMsg = new SceneEnterSyncMsg(playerEntity.getRid(), scene.getId(), playerEntity, scene.getGridManager().getCurrObserverPlayers(playerEntity));
        NetMsg myNetMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.SCENE_ENTER_SYNC, -1, sceneEnterSyncMsg, System.currentTimeMillis());
        sendMsg(playerEntity.getRid(), myNetMsg);
    }

    public static void sendAOIUpdateMsg(String rid, List<String> leaveRids, List<PlayerEntity> enterPlayers) {
        AOIUpdateSyncMsg aoiUpdateSyncMsg = new AOIUpdateSyncMsg(leaveRids, enterPlayers.stream().map(PlayerEntity::getTransformComponent).collect(Collectors.toList()));
        NetMsg aoiUpdate = NetUtils.buildSceneSyncMsg(SceneMsgConsts.AOI_UPDATE, -1, aoiUpdateSyncMsg, System.currentTimeMillis());
        sendMsg(rid, aoiUpdate);
    }

    public static void broadcastEnterGridMsg(List<String> toRids, List<PlayerEntity> enterPlayers) {
        PlayerEnterGridMsg playerEnterGridMsg = new PlayerEnterGridMsg(enterPlayers);
        NetMsg enterMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.ENTER_GRID_SYNC, -1, playerEnterGridMsg, System.currentTimeMillis());
        broadcast(toRids, enterMsg);
    }
}
