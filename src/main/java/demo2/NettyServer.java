package demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;


/**
 * @description:
 * @author: xwy
 * @create: 6:52 PM 2020/8/2
 **/

public class NettyServer {
    private static final String IP = "127.0.0.1";
    private static final int port = 6666;
    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors();

    private static final int BIZTHREADSIZE = 100;

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workGrop = new NioEventLoopGroup(BIZTHREADSIZE);

    public static void start() throws Exception{
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGrop)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new TcpServerHandler());
                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(IP, port).sync();
        channelFuture.channel().closeFuture().sync();
        System.out.println("server start");
    }

    protected static void shutDown(){
        workGrop.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }


    public static void main(String[] args) throws Exception {
        System.out.println("启动server...");
        NettyServer.start();
    }

}