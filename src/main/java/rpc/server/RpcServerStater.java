package rpc.server;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 10:05 PM 2020/8/9
**/

public class RpcServerStater {
    public static void main(String[] args) throws Exception {
        RpcServer server  = new RpcServer();
        server.publish("rpc.service");
        server.start();
    }
}