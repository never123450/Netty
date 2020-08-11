package dubboRPC.client.discovery;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 6:35 PM 2020/8/10
**/

public class ServiceDIscoveryTest {
    public static void main(String[] args) throws Exception {
        System.out.println(new ServiceDiscoveryImpl().discovery("dubboRPC.api.ZKConstant"));
    }
}