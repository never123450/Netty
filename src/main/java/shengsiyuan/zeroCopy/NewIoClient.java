package shengsiyuan.zeroCopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: xwy
 * @create: 下午3:31 2021/4/19
 **/

public class NewIoClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "~/Downloads/javaSoft";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送总字节数: " + transferCount + " 耗时: " + (System.currentTimeMillis() - startTime));

    }
}