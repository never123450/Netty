package demo.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @description:
 * @author: xwy
 * @create: 5:12 PM 2020/7/4
 **/

public class Server {
    public static void main(String[] args) {
        // 用于处理客户端连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 用于处理客户端请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//指定 eventLoop
//                    .childOption(ChannelOption.TCP_NODELAY, true)
//                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
//                    .handler(new ServerHandler()) //
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 管道初始化器
                        // 当初始化完成后就会触发该方法,用户初始化 Channel
                        protected void initChannel(SocketChannel ch) {
                            // 从 Channel 中获取 pipeline
                            ChannelPipeline pipeline = ch.pipeline();
                            // 将 HttpServerCodec 放到最后
                            // HttpServerCode 是什么? 是 HttpServerEncoder 和 HttpServerDncoder 的复合体
                            // HTTPRequestEncoder:http 响应请求解码器,将 Channel 中的ByteBuf数据解码为 HttpRequest 对象
                            // HttpResponseEncoder:http 响应解码器,将 HttpResponse 对象编码为将要在 Channel 中发送 ByteBuffe r的对象
                            pipeline.addLast(new HttpServerCodec());
                            // 将自定义的处理器放入到 pipeline 的最后
                            pipeline.addLast(new ServerHandler());
                        }
                    }); // 指定 childGroup 中的 eventLoop 所绑定的线程所要处理的处理器
            // 指定当前服务器所监听的端口号
            // bind 方法执行的是异步的
            // sync 方法会使bind 操作与后续的代码执行由异步变为同步
            ChannelFuture f = b.bind(8888).sync();
            System.out.println("服务启动成功 监听端口号为 : 8888");
            // closeFuture 的执行是异步的 当 channel 调用了close 方法并成功关闭后才会触发
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}