package com.xuren.game.common.net.channel;

/**
 * @author xuren
 */
public abstract class NetChannel implements INetChannel{
    private String rid;
    @Override
    public void setRid(String rid) {
        this.rid = rid;
    }
}
