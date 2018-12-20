package com.liuty.maven.mbean.impl;

import com.liuty.maven.mbean.ShutdownLatchMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShutdownLatch implements ShutdownLatchMBean {

    protected AtomicBoolean running = new AtomicBoolean(false);

    public long checkInterValInSeconds = 10;

    private String domain = "com.liuty.liftcycles";

    public ShutdownLatch() {
    }

    public ShutdownLatch(String domain) {
        this.domain = domain;
    }

    public void await() throws Exception {
        if (running.compareAndSet(false, true)) {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.registerMBean(this
                    , new ObjectName(domain, "name", "ShutdownLatch"));
            while(running.get()) {
                TimeUnit.SECONDS.sleep(checkInterValInSeconds);
            }
        }
    }

    @Override
    public String shutdown() {
        if (running.compareAndSet(true, false)) {
            return "shutdown signal sent, shutting down...";
        } else {
            return "shutdown signal had bean sent, no need again and again and again...";
        }
    }
}
