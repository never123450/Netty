package demo.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: xwy
 * @create: 3:11 PM 2020/7/4
 **/

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，端口：" + port);
        } catch (IOException exception) {
            System.out.println("服务启动失败");
        }
    }

    public void start(){
        new Thread(()->{
            doStart();
        }).start();
    }

    private void doStart() {
        while (true){
            try {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                System.out.println("服务端异常");
            }
        }
    }


}