package com.xuren.game.common.exception;

import com.xuren.game.common.exception.enums.CodeEnum;

/**
 * @author xuren
 */
public class CheckException extends BaseException{
    public CheckException(CodeEnum codeEnum, String fmt, Object... args) {
        super(codeEnum, fmt, args);
    }
}
