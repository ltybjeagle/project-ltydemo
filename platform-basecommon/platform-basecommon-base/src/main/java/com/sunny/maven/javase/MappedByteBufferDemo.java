package com.sunny.maven.javase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.Scanner;

/**
 * @author SUNNY
 * @description: 内存的缓冲区
 * @create: 2021-07-29 17:31
 */
public class MappedByteBufferDemo {
    public static void main(String ...args) throws Exception {
        File file = new File("D:\\test.txt");
        long len = file.length();
        byte[] bs= new byte[(int) len];
        MappedByteBuffer mappedByteBuffer =
                new FileInputStream(file).getChannel().map(FileChannel.MapMode.READ_ONLY, 0, len);
        for (int offset = 0; offset < len; offset++) {
            byte b = mappedByteBuffer.get();
            bs[offset] = b;
        }
        Scanner scan = new Scanner(new ByteArrayInputStream(bs)).useDelimiter(" ");
        while (scan.hasNext()) {
            System.out.println(scan.next() + " ");
        }

        // 直接传输
        FileInputStream fis = new FileInputStream(file);
        FileChannel fileChannel = fis.getChannel();
        fileChannel.transferTo(0, fileChannel.size(), Channels.newChannel(System.out));
    }
}
