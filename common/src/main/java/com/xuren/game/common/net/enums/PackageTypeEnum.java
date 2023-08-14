package com.xuren.game.common.net.enums;

import java.util.HashMap;

/**
 * @author xuren
 */
public enum PackageTypeEnum {
    REQUEST(0x00),
    SCENE_EVENT(0x01),
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
