package rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @description:
 * @author: xwy
 * @create: 8:10 PM 2020/8/9
 **/

public class RpcServer {

    // 定义服务注册表 key 为微服务名称,即业务接口名 value 为提供者实例
    private Map<String, Object> register = new HashMap<>();

    // 用于缓存提供者的类名
//    private List<String> classCache = new ArrayList<>();
    private List<String> classCache = Collections.synchronizedList(new ArrayList<>());

    // 将指定包中所有提供者进行发布 (写入注册表)
    public void publish(String providerPackage) throws Exception {
        // 将指定包中的提供者类名写入到缓存
        cacheProviderClass(providerPackage);
        // 进行注册(服务名称与提供者实例的映射写入到注册表
        doRegister();
    }

    private void doRegister() throws Exception {

        if (classCache.size() == 0) return;

        for (String className : classCache) {
            Class<?> aClass = Class.forName(className);
            register.put(aClass.getInterfaces()[0].getName(), aClass.newInstance());
        }

    }

    // 启动当前 server
    public void start(){
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childtGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parentGroup,childtGroup)
                    // 用于指定当服务端的请求处理线程全被占用时,临时存放已经完成了三次握手的队列的长度,默认 50
                    .option(ChannelOption.SO_BACKLOG,1024)
                    // 指定启用心跳机制来维护这个长度
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));

                            pipeline.addLast(new RpcServerHandler(register));
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(8888).sync();
            System.out.println("微服务已注册完毕!");
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childtGroup.shutdownGracefully();
        }
    }

    private void cacheProviderClass(String providerPackage) {
        // 获取指定包中的资源
        // rpc.server -> rpc/server
        URL resource = this.getClass().getClassLoader().getResource(providerPackage.replaceAll("\\.", "/"));

        if (resource == null) return;

        File dir = new File(resource.getFile());
        for (File file : dir.listFiles()) {
            // 遍历指定包及其子孙包中的所有文件
            if (file.isDirectory()) {
                // 递归查找子目录
                cacheProviderClass(providerPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String fileName = file.getName().replace(".class", "").trim();
                // 将当前文件的全限定类名写入 cache
                classCache.add(providerPackage + "." + fileName);
            }
        }

        System.out.println(classCache);
    }

}