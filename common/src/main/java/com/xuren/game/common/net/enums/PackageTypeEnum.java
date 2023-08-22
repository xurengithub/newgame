package com.xuren.game.common.net.enums;

import java.util.HashMap;

/**
 * @author xuren
 */
public enum PackageTypeEnum {
    REQUEST(0x00),
    SCENE_EVENT(0x01),
    RESPONSE(0x02),
    SCENE_SYNC(0x03),
    // 会等待客户端确认
    PUSH(0x04),
    PUSH_CONFIRM(0x05),
        ;

    private byte key;

    private static HashMap<Byte, PackageTypeEnum> map = new HashMap<>();

    static {
        for (PackageTypeEnum t : values()) {
            map.put(t.key, t);
        }
    }

    PackageTypeEnum(int key) {
        this.key = (byte) key;
    }

    public byte value() {
        return key;
    }

    public static PackageTypeEnum getPackageEnum(byte b) {
        return map.get(b);
    }
}
