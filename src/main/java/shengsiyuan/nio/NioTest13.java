package shengsiyuan.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @description:
 * @projectName:Netty
 * @see:shengsiyuan.nio
 * @author:xwy
 * @createTime:2021/4/18 22:11
 * @version:1.0
 */
public class NioTest13 {
    public static void main(String[] args) throws Exception {
        String inputFile = "in.txt";
        String outputFile = "out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        long inputLength = new File(inputFile).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        System.out.println("------------------------");
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        System.out.println("------------------------");

        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);

        ByteBuffer outputData = encoder.encode(charBuffer);

        outputFileChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

    }
}
