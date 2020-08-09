package webChart;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @description:
 * @author: xwy
 * @create: 5:25 PM 2020/8/9
 **/

public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    // 创建一个 ChannelGroup ,其是一个线程安全的集合,其中存放着与当前服务器相连接的所有 Active 状态的 Channel
    // GlobalEventExecutor 是一个单例,单线程的 EventExecutor ,是为了保证当前group 中的所有的 channel的处理
    // 线程是同一个线程
    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    // 只要有客户端 channel 给当前的服务端发送消息,那就会执行该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取到向服务器发送消息的 channel
        Channel channel = ctx.channel();
        // 这里要实现将消息广播给所有 group  中客户端channel
        // 发送给自己的消息与发送给大家的消息是不一样的
        group.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush(channel.remoteAddress() + " . " + msg + "\n");
            } else {
                channel.writeAndFlush(" me " + msg + "\n");
            }
        });
    }

    // 只要有客户端 channel 与服务端连接成功就会执行这个方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取到与服务器连接成功的 channel
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线! ");
        group.writeAndFlush(channel.remoteAddress() + " 上线! \n ");
        // 将当前 channel 添加到 group 中
        group.add(channel);
    }

    // 只要有客户端 channel 断开与服务端的连接就会执行这个方法
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 获取到当前要断开连接的 channel
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线! ");
        group.writeAndFlush(channel.remoteAddress() + " 下线! 当前在线人数: " + group.size() +"\n" );

        // group 中存放的都是 active 状态的 channel,一旦某状态下不是 active,group 会自动将其从集合中剔除,所以
        // 下面语句不用谢,
        // remove()方法的应用场景是,将一个 active状态下的 channel 移除
        // group.remove(channel);
    }
}