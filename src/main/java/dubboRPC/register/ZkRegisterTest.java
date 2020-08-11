package dubboRPC.register;

/**
 *
 * @description: 测试注册中心
 *
 * @author: xwy
 *
 * @create: 5:15 PM 2020/8/10
**/

public class ZkRegisterTest {

    public static void main(String[] args) throws Exception {
        new ZkRegistry().register("dubboRPC.api.ZKConstant","localhost:8888");
    }
}