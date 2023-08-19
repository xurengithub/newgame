package com.xuren.game.common.db.mongo;

import com.xuren.game.common.config.HostConfig;

import java.util.List;

public class MongoConfig {
    private String username;
    private String password;
    private String dbName;
    private String authDbName;
    private List<HostConfig> cluster;
    private String ReplicaSetName;
    private int maxConnectionPoolSize;
    public static final MongoConfig instance = new MongoConfig();
    private MongoConfig() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<HostConfig> getCluster() {
        return cluster;
    }

    public void setCluster(List<HostConfig> cluster) {
        this.cluster = cluster;
    }

    public String getReplicaSetName() {
        return ReplicaSetName;
    }

    public void setReplicaSetName(String replicaSetName) {
        ReplicaSetName = replicaSetName;
    }

    public int getMaxConnectionPoolSize() {
        return maxConnectionPoolSize;
    }

    public void setMaxConnectionPoolSize(int maxConnectionPoolSize) {
        this.maxConnectionPoolSize = maxConnectionPoolSize;
    }

    public String getAuthDbName() {
        return authDbName;
    }

    public void setAuthDbName(String authDbName) {
        this.authDbName = authDbName;
    }
}
