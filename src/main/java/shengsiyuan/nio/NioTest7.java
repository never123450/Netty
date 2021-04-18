package shengsiyuan.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @description:
 * @author: xwy
 * @create: 上午7:39 2021/4/18
 **/

public class NioTest7 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest1.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        FileLock fileLock =fileChannel.lock(3,6,true);
        System.out.println("valid: " + fileLock.isValid());
        System.out.println("lock type: " + fileLock.isShared());

        fileLock.release();

        randomAccessFile.close();
    }
}