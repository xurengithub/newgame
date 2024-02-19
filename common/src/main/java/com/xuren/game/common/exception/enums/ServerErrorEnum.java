package com.xuren.game.common.exception.enums;

/**
 * @author xuren
 */
public enum ServerErrorEnum implements CodeEnum {
    //region 系统内部错误 1-1000
    NUMERIC_NOT_FOUND(1, "数值未查找到"),

    //endregion

    //region 业务错误 2000-10000

    //endregion
    ;

    ServerErrorEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
