package rpc.client.consomer;

import rpc.client.PrcProxy;
import rpc.service.SomeService;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 10:08 PM 2020/8/9
**/

public class RpcConsumerTest {
    public static void main(String[] args) {
        SomeService service = PrcProxy.create(SomeService.class);
        System.out.println(service.hello("xwt"));
        System.out.println(service.hashCode());
    }
}