package com.sunny.maven.rpc.test.scanner;

import com.sunny.maven.rpc.common.scanner.ClassScanner;
import com.sunny.maven.rpc.common.scanner.reference.RpcReferenceScanner;
import com.sunny.maven.rpc.provider.common.scanner.RpcServiceScanner;
import org.junit.Test;

import java.util.List;

/**
 * @author SUNNY
 * @description: Scanner测试类
 * @create: 2022-12-23 18:06
 */
public class ScannerTest {

    /**
     * 扫描com.sunny.maven.rpc.test.scanner包下所有类
     */
    @Test
    public void testScannerClassNameList() throws Exception {
        List<String> classNameList = ClassScanner.getClassNameList("com.sunny.maven.rpc.test.scanner");
        classNameList.forEach(System.out::println);
    }

    /**
     * 扫描com.sunny.maven.rpc.test.scanner包下所有标注了@RpcService注解的类
     */
    @Test
    public void testScannerClassNameListByRpcService() throws Exception {
        RpcServiceScanner.doScannerWithRpcServiceAnnotationFilterAndRegistryService(
                "com.sunny.maven.rpc.test.scanner");
    }

    /**
     * 扫描com.sunny.maven.rpc.test.scanner包下所有标注了@RpcReference注解的类
     * @throws Exception
     */
    @Test
    public void testScannerClassNameListByRpcReference() throws Exception {
        RpcReferenceScanner.doScannerWithRpcReferenceAnnotationFilter("com.sunny.maven.rpc.test.scanner");
    }
}
