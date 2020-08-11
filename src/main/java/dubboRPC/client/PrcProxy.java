package dubboRPC.client;

import dubboRPC.client.discovery.ServiceDiscovery;
import dubboRPC.client.discovery.ServiceDiscoveryImpl;
import dubboRPC.client.loadBalance.RandomLoadBalance;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import rpc.api.dto.InvokerMessage;
import rpc.client.PrcClientHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: xwy
 * @create: 10:11 PM 2020/8/9
 **/

public class PrcProxy {

    public static <T> T create(Class<?> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 若目标方法是 Object 的方法,则直接进行本地调用
                if (Object.class.equals(method.getDeclaringClass())) {
                    return method.invoke(this, args);
                }
                // 远程调用
                return rpcInvoker(clazz, method, args);
            }


        });
    }

    private static Object rpcInvoker(Class<?> clazz, Method method, Object[] args) {

        PrcClientHandler handler = new PrcClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    // Nagle 算法
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(handler);
                        }
                    });

            ServiceDiscovery discovery = new ServiceDiscoveryImpl();
            RandomLoadBalance randomLoadBalance = new RandomLoadBalance();
            String serverAddress = randomLoadBalance.choose(discovery.discovery(clazz.getName()));
            if (serverAddress == null) {
                throw new Exception("没有指定服务的提供者");
            }

            String ip = serverAddress.split(":")[0];
            String portStr = serverAddress.split(":")[1];
            Integer port = Integer.valueOf(portStr);

            ChannelFuture future = bootstrap.connect(ip, port).sync();

            // 远程调用的信息
            InvokerMessage message = new InvokerMessage();
            message.setClassName(clazz.getName());
            message.setMethodName(method.getName());
            message.setParamTypes(method.getParameterTypes());
            message.setParamValues(args);

            // 将调用信息发送给 server
            future.channel().writeAndFlush(message);

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return handler.getResult();
    }
}