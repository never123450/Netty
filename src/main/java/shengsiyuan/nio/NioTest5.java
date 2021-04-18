package shengsiyuan.nio;

import java.nio.ByteBuffer;

/**
 * @description:
 * @author: xwy
 * @create: 下午3:43 2021/4/17
 **/

public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        byteBuffer.putInt(15);
        byteBuffer.putLong(5000000L);
        byteBuffer.putDouble(14.123456);
        byteBuffer.putChar('你');
        byteBuffer.putShort((short) 2);
        byteBuffer.putChar('我');

        byteBuffer.flip();

        // 必须按顺序
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());

    }
}