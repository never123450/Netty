package shengsiyuan.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @description:
 * java.io 中最为核心的一个概念是流(Stream),面向流的编程.java 中,一个流要么是输入流,要么是输出流,
 * 不可能同时是输入流又是输出流
 * java,nio 中拥有 3 个核心概念:Selector,Channel 与 Buffer,在 java.nio 中,我们是面向块(block)
 * 或是缓冲区(Buffer)编程的 . Buffer 本身就是一块内存,底层实现上,它实际上是个数组.数据的读写都是通过 Buffer 来实现的
 *
 * 除了数组之外,Buffer 还提供了对于数据的结构化访问方式,并且可以追踪到系统的读写过程
 *
 * java 中的 8 中原生数据类型都有各自对应的 Buffer 类型 ,比如: IntBuffer,ByteBuffer......
 *
 * Channel 指的是可以向其写入数据或是从中读取数据的对象,它类似于 java.io 中的 Stream
 *
 * 所有数据的读写都是通过 Buffer 类进行的,永远不会出现直接向 Channel 写入数据的情况,或是直接从 Channel读取数据的情况
 *
 * 与 Stream 不同的是,Channel 是双向的,一个流只可能是 InputStream 或是 OutputStream,Channel打开后
 * 则可以j进行读取,写入或者读写
 *
 * 由于 Channel 是双向的,因此它能更好的反映出底层操作系统的真实情况;在 Linux 系统中,底层操作系统的通道就是双向的
 *
 *
 * @author: xwy
 * @create: 上午9:43 2021/4/17
 **/

public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        System.out.println("before flip limit: " + buffer.limit());
        buffer.flip();
        System.out.println("after flip limit: " + buffer.limit());
        System.out.println("enter while loop");

        while (buffer.hasRemaining()) {
            System.out.println("position: " + buffer.position());
            System.out.println("limit: " + buffer.limit());
            System.out.println("capacity: " + buffer.capacity());

            System.out.println(buffer.get());
        }
    }
}