package com.xuren.game.common.net;

import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;

/**
 * @author xuren
 */
public class NetMsg implements Cleanable {
    private TypeEnum type;
    private PackageTypeEnum packageTypeEnum;

    private int requestId = -1;

    private int msgCode = -1;

    private String rid;
    private byte[] data;

    private String version = "";

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public PackageTypeEnum getPackageTypeEnum() {
        return packageTypeEnum;
    }

    public void setPackageTypeEnum(PackageTypeEnum packageTypeEnum) {
        this.packageTypeEnum = packageTypeEnum;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void clear() {
        type = null;
        packageTypeEnum = null;
        data = null;
        msgCode = -1;
        requestId = -1;
        rid = null;
        version = "";
    }
}
