package com.xuren.game.proto;

import com.xuren.game.common.proto.MsgBase;

public class MsgLogin extends MsgBase {
    public MsgLogin() {protoName = "MsgLogin";}
    //客户端发
    private String id = "";
    private String pw = "";
    //服务端回（0-成功，1-失败）
    private int result = 0;
    private PlayerSimpleInfo[] playerSimpleInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public PlayerSimpleInfo[] getPlayerSimpleInfos() {
        return playerSimpleInfos;
    }

    public void setPlayerSimpleInfos(PlayerSimpleInfo[] playerSimpleInfos) {
        this.playerSimpleInfos = playerSimpleInfos;
    }
}
