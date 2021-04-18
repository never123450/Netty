package shengsiyuan.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: xwy
 * @create: 上午10:13 2021/4/17
 **/

public class NioTest2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        int read = fileChannel.read(byteBuffer);

        // 操作反转 读->写
        byteBuffer.flip();

        while (byteBuffer.remaining() > 0) {
            byte b = byteBuffer.get();
            System.out.println("Character : " + (char) b);
        }
        fileChannel.close();
    }
}