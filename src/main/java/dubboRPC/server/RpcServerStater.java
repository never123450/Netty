package dubboRPC.server;

import dubboRPC.register.ZkRegistry;

/**
 * @description:
 * @author: xwy
 * @create: 10:05 PM 2020/8/9
 **/

public class RpcServerStater {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        ZkRegistry zkRegistry = new ZkRegistry();

        server.publish(zkRegistry,"localhost:8888","SomeService");
        server.start();
    }
}