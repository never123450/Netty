package dubboRPC.client.loadBalance;

import java.util.List;

/**
 *
 * @description: 负载均衡
 *
 * @author: xwy
 *
 * @create: 5:53 PM 2020/8/10
**/

public interface LoadBalance {
    /**
     * 选择 server
     * @param servers 所有的提供者
     * @return
     */
    String choose(List<String> servers);
}