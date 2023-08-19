package com.xuren.game.common.config;

public class BaseConfig {
    private static final BaseConfig instance = new BaseConfig();
    private String sec;
    public static BaseConfig getInstance() {
        return instance;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
