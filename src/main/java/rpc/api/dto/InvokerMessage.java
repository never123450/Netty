package rpc.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @description: Client 发送给 Server 的服务调用信息
 *
 * @author: xwy
 *
 * @create: 9:28 PM 2020/8/9
**/
@Data
public class InvokerMessage implements Serializable{
    // 接口名,即服务名称
    private String className;

    // 要调用的方法名
    private String methodName;

    // 方法参数类型
    private Class<?> [] paramTypes;

    // 方法参数类型
    private Object[] paramValues;
}