package dubboRPC.client.discovery;

import dubboRPC.api.ZKConstant;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @description:
 * @author: xwy
 * @create: 6:04 PM 2020/8/10
 **/

public class ServiceDiscoveryImpl implements ServiceDiscovery {

    private List<String> servers;

    @Override
    public List<String> discovery(String serviceName) throws Exception {
        String servicePath = ZKConstant.ZK_DUBBO_ROOT_PATH + "/" + serviceName;
        servers = curator.getChildren().forPath(servicePath);
        // 为微服务节点注册 watcher 监听
        registerWatch(servicePath);
        return servers;
    }

    private void registerWatch(String servicePath) throws Exception {
        // 可以缓存指定节点及其子节点的信息
        PathChildrenCache cache = new PathChildrenCache(curator, servicePath, true);

        // 为 cache 添加监听
        cache.getListenable().addListener((client, event) -> {
            servers = client.getChildren().forPath(servicePath);
        });

        // 启动监听
        cache.start();
    }


    private CuratorFramework curator;

    public ServiceDiscoveryImpl() {
        curator = CuratorFrameworkFactory.builder()
                //指定要连接的 zk 集群
                .connectString(ZKConstant.ZK_CLUSTER)
                // 连接超时时限
                .connectionTimeoutMs(10000)
                // 会话超时时限
                .sessionTimeoutMs(4000)
                // 指定重试机制:每重试一次,休眠 1s,最多重试 10 次
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();

        // zk 客户端启动
        curator.start();
    }


}