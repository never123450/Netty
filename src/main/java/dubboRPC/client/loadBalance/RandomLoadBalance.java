package dubboRPC.client.loadBalance;

import java.util.List;
import java.util.Random;

/**
 *
 * @description:  随机 负载均衡
 *
 * @author: xwy
 *
 * @create: 5:55 PM 2020/8/10
**/

public class RandomLoadBalance implements LoadBalance{
    @Override
    public String choose(List<String> servers) {
        if (servers == null) {
            return null;
        }
        if (servers.size() == 1){
            return servers.get(0);
        }

        int i = new Random().nextInt(servers.size());
        return servers.get(i);
    }
}