package dubboRPC.client.discovery;

import java.util.List;

/**
 * @description: 服务发现规范
 * @author: xwy
 * @create: 6:02 PM 2020/8/10
 **/

public interface ServiceDiscovery {

    /**
     * 服务发现
     *
     * @param serviceName 服务名称,即接口名
     * @return 返回服务发现的主机信息 ip:port
     */
    List<String> discovery(String serviceName) throws Exception;
}