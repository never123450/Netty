package rpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.api.dto.InvokerMessage;

import java.util.Map;

/**
 * @description:
 * @author: xwy
 * @create: 8:57 PM 2020/8/9
 **/

public class RpcServerHandler extends SimpleChannelInboundHandler<InvokerMessage> {

    private Map<String, Object> register;

    public RpcServerHandler(Map<String, Object> register) {
        this.register = register;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokerMessage msg) throws Exception {
        Object result = "没有该提供者";

        // 判断注册表中是否存在要调用的服务
        if (register.containsKey(msg.getClassName())) {
            // 从注册表中获取相应的提供者实例
            Object provider = register.get(msg.getClassName());
            // 为消费者提供远程调用的方法的真正执行
            result = provider.getClass().getMethod(msg.getMethodName(),
                    msg.getParamTypes()).invoke(provider, msg.getParamValues());
            ctx.writeAndFlush(result);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}