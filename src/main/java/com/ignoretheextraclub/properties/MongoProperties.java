package com.ignoretheextraclub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by caspar on 12/02/17.
 */
@ConfigurationProperties
public class MongoProperties
{
    private String dbName;
    private String host;
    private int port;
    private int connectTimeout = 2000;

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getDbName()
    {
        return dbName;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }
}
