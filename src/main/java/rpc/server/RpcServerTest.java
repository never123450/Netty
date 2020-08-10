package rpc.server;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 8:24 PM 2020/8/9
**/

public class RpcServerTest {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        server.publish("rpc.service.impl");
        server.start();
    }
}