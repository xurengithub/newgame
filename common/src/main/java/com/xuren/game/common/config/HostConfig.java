package com.xuren.game.common.config;

public class HostConfig {
    private String host;
    private int port;
    public HostConfig() {

    }
    public HostConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
