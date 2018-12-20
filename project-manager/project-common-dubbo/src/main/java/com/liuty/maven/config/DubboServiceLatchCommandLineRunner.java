package com.liuty.maven.config;

import com.liuty.maven.mbean.impl.ShutdownLatch;
import org.springframework.boot.CommandLineRunner;

public class DubboServiceLatchCommandLineRunner implements CommandLineRunner {

    private String domain = "com.liuty.maven.management";

    @Override
    public void run(String... strings) throws Exception {
        ShutdownLatch latch = new ShutdownLatch(getDomain());
        latch.await();
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
