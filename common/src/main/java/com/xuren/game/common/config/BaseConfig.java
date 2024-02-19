package com.xuren.game.common.config;

public class BaseConfig {
    private static final BaseConfig instance = new BaseConfig();
    private String sec;

    private int netPort;

    private int rpcPort;

    private String numericPath;
    private String numericPackages;


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

    public String getNumericPath() {
        return numericPath;
    }

    public void setNumericPath(String numericPath) {
        this.numericPath = numericPath;
    }

    public String getNumericPackages() {
        return numericPackages;
    }

    public void setNumericPackages(String numericPackages) {
        this.numericPackages = numericPackages;
    }
}
