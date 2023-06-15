package com.sunny.maven.core.demo.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Base64;

/**
 * @author SUNNY
 * @description: KeyTool工具使用
 * @create: 2023/6/10 12:51
 */
public class KeyToolTest {

    public static void main(String[] args) {
        /*
         * 生成证书:
         * keytool -genkey -alias nginxssl -keypass 123456 -keyalg RSA -keysize 1024 -validity 365
         * -keystore D:\nginx-1.22.0\ssl\nginxssl.keystore -storepass 123456
         * 导出证书：
         * keytool -export -alias nginxssl -keystore D:\nginx-1.22.0\ssl\nginxssl.keystore
         * -storepass 123456 -rfc -file D:\nginx-1.22.0\ssl\nginxssl.cer
         */
        // 解析出密钥
        try {
            // 读取文件内容
            FileInputStream is = new FileInputStream("D:\\nginx-1.22.0\\ssl\\nginxssl.keystore");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(is, "123456".toCharArray());
            PrivateKey key = (PrivateKey) ks.getKey("nginxssl", "123456".toCharArray());
            System.out.println("key=" + new String(Base64.getEncoder().encode(key.getEncoded())));
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
