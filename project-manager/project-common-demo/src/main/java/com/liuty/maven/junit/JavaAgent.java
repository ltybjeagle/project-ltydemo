package com.liuty.maven.junit;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JavaAgent implements ClassFileTransformer {

    public final String injectedClassName = "gov.mof.fasp2.buscommon.ui.datatable.BusDataTableBO";
    public final String methodName = "queryPageData";
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined
            , ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        if (className.equals(injectedClassName)) {
            CtClass ctclass = null;
            try {
                ctclass = ClassPool.getDefault().get(className);
                CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                ctmethod.insertBefore("System.out.println(123123)");
                return ctclass.toBytecode();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
