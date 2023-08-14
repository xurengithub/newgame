package com.xuren.game.proto;

import com.xuren.game.common.proto.MsgBase;

public class MsgKick extends MsgBase {
    //原因（0-其他人登陆同一账号）
    private int reason = 0;
    public MsgKick() {protoName = "MsgKick";}

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
