package com.xuren.game.net;

import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.NetUtils;
import com.xuren.game.logic.scene.Scene;
import com.xuren.game.logic.scene.consts.SceneMsgConsts;
import com.xuren.game.logic.scene.entities.PlayerEntity;
import com.xuren.game.logic.scene.syncmsg.LeaveSceneSyncMsg;
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

    public static void broadcastLeaveMsg(List<String> leaveRids, PlayerEntity playerEntity) {
        LeaveSceneSyncMsg leaveSceneSyncMsg = new LeaveSceneSyncMsg(playerEntity.getRid());
        NetMsg leaveMsg = NetUtils.buildSceneSyncMsg(SceneMsgConsts.LEAVE_SCENE_SYNC, -1, leaveSceneSyncMsg, System.currentTimeMillis());
        broadcast(leaveRids, leaveMsg);
    }
}
