package demo.socket;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 3:23 PM 2020/7/4
**/

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws Exception{
        final Socket socket = new Socket(HOST,PORT);
        new Thread(()->{
            System.out.println("客户端启动成功");
            while (true){
                String message = "hello world";
                System.out.println("客户端发送数据："+message);
                try {
                    socket.getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sleep();
            }
        }).start();
    }

    private static void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}