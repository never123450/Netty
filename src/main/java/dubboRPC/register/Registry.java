package dubboRPC.register;

/**
 *
 * @description: 注册中心规范
 *
 * @author: xwy
 *
 * @create: 4:50 PM 2020/8/10
**/

public interface Registry {

    /**
     *
     * @param serviceName 服务名称,即业务接口名
     * @param serviceAddress 提供者主机的 ip:port
     */
    void register(String serviceName,String serviceAddress) throws Exception;

}