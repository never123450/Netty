package dubboRPC.register;

import dubboRPC.api.ZKConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @description: zk 作为注册中心
 * @author: xwy
 * @create: 4:52 PM 2020/8/10
 **/

public class ZkRegistry implements Registry {

    private CuratorFramework curator;

    public ZkRegistry() {
        this.curator = CuratorFrameworkFactory.builder()
                // 指定要连接的 zk 集群
                .connectString(ZKConstant.ZK_CLUSTER)
                // 连接超时时限
                .connectionTimeoutMs(10000)
                // 会话超时时限
                .sessionTimeoutMs(500)
                // 指定重试机制:每重试一次,休眠 1s,最多重试 10 次
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();

        // zk 客户端启动
        curator.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        // 1.创建服务名称的持久节点
        String servicePath = ZKConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;

        if (curator.checkExists().forPath(servicePath) == null) {
            curator.create()
                    // 若父节点不存在,则创建父节点
                    .creatingParentsIfNeeded()
                    // 指定持久节点
                    .withMode(CreateMode.PERSISTENT)
                    // 指定要创建的节点
                    .forPath(servicePath);
        }


        // 2.创建 ip:port 的临时节点
        String addressPath = servicePath + "/" + serviceAddress;
        if (curator.checkExists().forPath(addressPath) == null) {
            String path = curator.create()
                    // 指定创建临时节点
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath);
            System.out.println(path);
        }

    }

}