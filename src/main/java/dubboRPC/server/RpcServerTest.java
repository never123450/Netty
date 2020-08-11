package dubboRPC.server;

import dubboRPC.register.ZkRegistry;

/**
 * @description: 要求:
 * 注册过程:
 * 1.在 zk 中创建一个 dubbo 的持久根节点,例如:/xdubbo
 * 2.以服务名称作为节点名称在/xdubbo 下创建一个持久节点,例如:/xdubbo/com.ab.service.Someservice
 * 3.在服务名称节点下创建临时节点,节点名称为当前提供者主机的 ip:port
 * 例如:/xdubbo/com.ab.service.Someservice/192.168.0.106:8888
 * <p>
 * 消费过程:
 * 1.从 zk 的/xdubbo 节点下下好大哦指定服务名称的子节点
 * 2.为该服务名称节点添加一个 watcher 监听,监听其子节点的变化
 * 3.获取该服务名称节点的所有子节点,即获取该服务名称的所有提供者主机的 ip:port
 * 4.通过负载均衡选择一个提供者主机
 * 5.将其调用信息发送给该选择的提供者主机
 * 6.提供者主机将其执行结果返回给消费者
 * @author: xwy
 * @create: 8:24 PM 2020/8/9
 **/

public class RpcServerTest {
    public static void main(String[] args) throws Exception {
        RpcServer server = new RpcServer();
        ZkRegistry registry = new ZkRegistry();
        server.publish(registry, "localhost:8888", "rpc.service.impl");
        server.start();
    }
}