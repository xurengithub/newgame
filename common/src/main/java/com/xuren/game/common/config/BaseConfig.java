package com.xuren.game.common.config;

public class BaseConfig {
    private static final BaseConfig instance = new BaseConfig();
    private String sec;

    private int netPort;

    private int rpcPort;


    public static BaseConfig getInstance() {
        return instance;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }

    public int getNetPort() {
        return netPort;
    }

    public void setNetPort(int netPort) {
        this.netPort = netPort;
    }

    public int getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(int rpcPort) {
        this.rpcPort = rpcPort;
    }
}
