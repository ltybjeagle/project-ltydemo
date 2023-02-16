package com.sunny.maven;

import com.netflix.appinfo.MyDataCenterInstanceConfig;
import org.apache.commons.lang.StringUtils;

/**
 * @author SUNNY
 * @description: MyDataCenterInstanceConfig for set value
 * @create: 2023-02-15 15:54
 */
public class CustomEurekaInstanceConfig extends MyDataCenterInstanceConfig {
    private String applicationName;
    private String instanceId;
    private String ipAddress;
    private int port = -1;
    @Override
    public String getInstanceId() {
        if (StringUtils.isBlank(instanceId)) {
            return super.getInstanceId();
        }
        return instanceId;
    }

    @Override
    public String getIpAddress() {
        if (StringUtils.isBlank(ipAddress)) {
            return super.getIpAddress();
        }
        return ipAddress;
    }

    @Override
    public int getNonSecurePort() {
        if (port == -1) {
            return super.getNonSecurePort();
        }
        return port;
    }

    @Override
    public String getAppname() {
        if (StringUtils.isBlank(applicationName)) {
            return super.getAppname();
        }
        return applicationName;
    }

    @Override
    public String getHostName(boolean refresh) {
        return this.getIpAddress();
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
