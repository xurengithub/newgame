package com.xuren.game.net;

import com.xuren.game.common.log.Log;
import com.xuren.game.common.net.NetMsg;

import java.util.List;

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
}
