package com.xuren.game.net;

import com.xuren.game.common.net.channel.INetChannel;
import org.testng.collections.Maps;

import java.util.Map;

/**
 * @author xuren
 */
public abstract class OnlineNetChannels {
    private static final Map<String, INetChannel> netChannelMap = Maps.newConcurrentMap();
    public static boolean containsKey(String key) {
        return netChannelMap.containsKey(key);
    }

    public static INetChannel getChannel(String key) {
        return netChannelMap.get(key);
    }

    public static void putChannel(String key, INetChannel netChannel) {
        netChannelMap.put(key, netChannel);
    }

    public static INetChannel removeChannel(String key) {
        return netChannelMap.remove(key);
    }

    public static boolean isOnline(String key) {
        INetChannel netChannel = netChannelMap.get(key);
        if (netChannel == null) {
            return false;
        }
        return netChannel.isAlive();
    }
}
