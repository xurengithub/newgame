package com.xuren.game.common.exception;

import com.xuren.game.common.exception.enums.CodeEnum;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author xuren
 */
public class BaseException extends RuntimeException{
    private CodeEnum codeEnum;
    private Object[] args;

    public BaseException(CodeEnum codeEnum, String fmt, Object... args) {
        super(MessageFormatter.arrayFormat(fmt, args).getMessage());
        this.codeEnum = codeEnum;
        this.args = args;
    }

    public CodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
