package com.ignoretheextraclub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by caspar on 02/04/17.
 */
@ConfigurationProperties(prefix = "graphite")
public class GraphiteProperties
{
    private boolean enabled;
    private String hostname;
    private int port;
    private String prefix;
    private int periodSeconds;

    public GraphiteProperties()
    {
    }

    public GraphiteProperties(final boolean enabled,
                              final String hostname,
                              final int port,
                              final String prefix,
                              final int periodSeconds)
    {
        this.enabled = enabled;
        this.hostname = hostname;
        this.port = port;
        this.prefix = prefix;
        this.periodSeconds = periodSeconds;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public int getPeriodSeconds()
    {
        return periodSeconds;
    }

    public void setPeriodSeconds(int periodSeconds)
    {
        this.periodSeconds = periodSeconds;
    }

    @Override
    public String toString()
    {
        return "GraphiteProperties{" +
                "enabled=" + enabled +
                ", hostname='" + hostname + '\'' +
                ", port=" + port +
                ", prefix='" + prefix + '\'' +
                ", periodSeconds=" + periodSeconds +
                '}';
    }
}
