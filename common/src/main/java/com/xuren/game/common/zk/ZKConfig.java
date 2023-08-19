package com.xuren.game.common.zk;


/**
 * @author xuren
 */
public class ZKConfig {
    private int retryCount = 5;
    private int retrySleepTimeMs = 1000;
    private int elapsedTimeMs = 5000;
    private String connectString = "127.0.0.1:2181";
    private int sessionTimeoutMs = 60000;
    private int connectionTimeoutMs = 5000;
    private String namespace = "game";
    public static final ZKConfig instance = new ZKConfig();
    private ZKConfig() {

    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetrySleepTimeMs() {
        return retrySleepTimeMs;
    }

    public void setRetrySleepTimeMs(int retrySleepTimeMs) {
        this.retrySleepTimeMs = retrySleepTimeMs;
    }

    public int getElapsedTimeMs() {
        return elapsedTimeMs;
    }

    public void setElapsedTimeMs(int elapsedTimeMs) {
        this.elapsedTimeMs = elapsedTimeMs;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}
