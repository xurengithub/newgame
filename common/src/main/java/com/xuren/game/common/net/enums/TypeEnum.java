package com.xuren.game.common.net.enums;

import java.util.HashMap;

/**
 * @author xuren
 */
public enum TypeEnum {
    DATA(0x00),
    INIT(0x01),
    ;

    private byte key;

    private static HashMap<Byte, TypeEnum> map = new HashMap<>();

    static {
        for (TypeEnum t : values()) {
            map.put(t.key, t);
        }
    }

    TypeEnum(int key) {
        this.key = (byte) key;
    }

    public byte value() {
        return key;
    }

    public static TypeEnum getTypeEnum(byte b) {
        return map.get(b);
    }
}
