package com.xuren.game.net;

import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.consts.SceneMsgConsts;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.syncmsg.AOIUpdateSyncMsg;
import com.xuren.game.logic.scene.syncmsg.LeaveSceneSyncMsg;
import com.xuren.game.logic.scene.syncmsg.SceneEnterSyncMsg;
import com.xuren.game.logic.scene.syncmsg.TransformSyncMsg;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuren
 */
public abstract class NetMsgSendUtils {
    public static void broadcast(List<String> rids, NetMsg netMsg) {
        for (var rid : rids) {
            var netChannel = OnlineNetChannels.getChannel(rid);
            if (netChannel != null && netChannel.isAlive()) {
                netChannel.sendMsg(netMsg);
            } else {
                Log.system.info("player:{} channel is not active", rid);
            }
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

    public static void broadcastLeaveMsg(List<String> leaveRids, String rid) {
        LeaveSceneSyncMsg leaveSceneSyncMsg = new LeaveSceneSyncMsg(rid);
        NetMsg leaveMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.LEAVE_SCENE_SYNC, -1, leaveSceneSyncMsg, System.currentTimeMillis());
        broadcast(leaveRids, leaveMsg);
    }

    public static void sendEnterSceneMsg(Scene scene, PlayerEntity playerEntity) {
        SceneEnterSyncMsg sceneEnterSyncMsg = new SceneEnterSyncMsg(playerEntity.getRid(), scene.getId(), playerEntity, scene.getGridManager().getCurrObserverPlayers(playerEntity));
        NetMsg myNetMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.SCENE_ENTER_SYNC, -1, sceneEnterSyncMsg, System.currentTimeMillis());
        NetMsgSendUtils.broadcast(List.of(playerEntity.getRid()), myNetMsg);
    }

    public static void sendAOIUpdateMsg(List<String> leaveRids, List<PlayerEntity> enterPlayers, PlayerEntity playerEntity) {
        AOIUpdateSyncMsg aoiUpdateSyncMsg = new AOIUpdateSyncMsg(leaveRids, enterPlayers.stream().map(PlayerEntity::getTransformComponent).collect(Collectors.toList()));
        NetMsg aoiUpdate = NetUtils.buildSceneSyncMsg(SceneMsgConsts.AOI_UPDATE, -1, aoiUpdateSyncMsg, System.currentTimeMillis());
        NetMsgSendUtils.broadcast(List.of(playerEntity.getRid()), aoiUpdate);
    }
}
