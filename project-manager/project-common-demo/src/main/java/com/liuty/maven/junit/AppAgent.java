package com.liuty.maven.junit;

import java.lang.instrument.Instrumentation;

public class AppAgent {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("AppAgent start execute ...");
        inst.addTransformer(new JavaAgent());
    }
}
