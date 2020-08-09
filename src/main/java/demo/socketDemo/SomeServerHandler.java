package demo.socketDemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: SimpleChannelInboundHandler 和 ChannelInboundHandlerAdapter 的区别:
 * SimpleChannelInboundHandler 中的 channelRead0 方法会自动释放接收到来自于对方 msg 的所占资源
 * ChannelInboundHandlerAdapter 不会自动是否接受到来自于对方 msg 的所占资源
 * 若对方没有向自己发送数据,则自定义处理器建议继承 ChannelInboundHandlerAdapter,因为若继承 SimpleChannelInboundHandler
 * 需要重写 channelRead0 方法 ,而重写该方法的目的是对来自于对方的数据进行处理,因为对方根本就没有发送数据,所以也就没有必要重写该方法
 * <p>
 * 继承 ChannelInboundHandlerAdapter .因为 write() 方法的执行是异步的且 SimpleChannelInboundHandler 中的 channelRead 方法
 * 会自动释放来自于对方的 msg
 * @author: xwy
 * @create: 2:03 PM 2020/8/6
 **/

//public class SomeServerHandler extends ChannelInboundHandlerAdapter {
public class SomeServerHandler extends SimpleChannelInboundHandler<String> {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        // 将来自于客户端的数据显示在服务端控制台
//        System.out.println(ctx.channel().remoteAddress() + " , " + msg);
//        // 向客户端发送数据
//        ctx.channel().writeAndFlush("from server : " + UUID.randomUUID());
//        TimeUnit.MILLISECONDS.sleep(500);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private int counter;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Server 端接收到的第 [ " + ++counter + "] 个数据包" + s);
    }

}