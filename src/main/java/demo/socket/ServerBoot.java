package demo.socket;

/**
 * @description:
 * @author: xwy
 * @create: 3:10 PM 2020/7/4
 **/

public class ServerBoot {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.start();
    }
}