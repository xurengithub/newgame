package com.xuren.game.proto;

public class MsgBuyItem extends MsgBase {
    private int result;
    private int itemId;
    public MsgBuyItem(){
        protoName = "MsgBuyItem";
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


}
