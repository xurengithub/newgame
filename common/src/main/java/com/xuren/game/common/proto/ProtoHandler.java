package com.xuren.game.common.proto;

import java.lang.annotation.*;

/**
 * @author xuren
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtoHandler {
    int module() default 0;
    String desc() default "";
}
