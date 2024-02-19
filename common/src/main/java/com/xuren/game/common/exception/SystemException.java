package com.xuren.game.common.exception;

import com.xuren.game.common.exception.enums.CodeEnum;

/**
 * @author xuren
 */
public class SystemException extends BaseException{
    public SystemException(CodeEnum codeEnum, String fmt, Object... args) {
        super(codeEnum, fmt, args);
    }
}
