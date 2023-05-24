package com.xuren.game.common.net;

import org.beykery.jkcp.KcpOnUdp;

/**
 * @author xuren
 */
public class KcpNetChannel extends NetChannel{
    private KcpOnUdp kcpOnUdp;
    public KcpNetChannel(KcpOnUdp kcpOnUdp) {
        this.kcpOnUdp = kcpOnUdp;
    }
    @Override
    public void sendMsg(Object msg) {
//        kcpOnUdp.send();
    }
}
