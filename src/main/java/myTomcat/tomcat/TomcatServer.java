package myTomcat.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.Map;

/**
 * @description:
 * @author: xwy
 * @create: 9:22 AM 2020/7/26
 **/

public class TomcatServer {

    private Map<String,Object> registerMap;

    public TomcatServer(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }

    public void start() {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new TomcatServerHandler(registerMap));
                        }
                    });
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("tomcat 启动成功，端口号：8888");
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}