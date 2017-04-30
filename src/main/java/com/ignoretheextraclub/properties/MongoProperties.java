package com.ignoretheextraclub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by caspar on 12/02/17.
 */
@ConfigurationProperties // Not currently being used because spring magic
public class MongoProperties
{
    private String dbName;
    private String host;
    private int port;
    private int connectTimeout = 2000;

    public String getDbName()
    {
        return dbName;
    }

    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getConnectTimeout()
    {
        return connectTimeout;
    }
}
