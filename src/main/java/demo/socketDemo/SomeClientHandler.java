package demo.socketDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @description: SimpleChannelInboundHandler 和 ChannelInboundHandlerAdapter 的区别:
 * SimpleChannelInboundHandler 中的 channelRead0 方法会自动释放接收到来自于对方 msg 的所占资源
 * ChannelInboundHandlerAdapter 不会自动是否接受到来自于对方 msg 的所占资源
 * 若对方没有向自己发送数据,则自定义处理器建议继承 ChannelInboundHandlerAdapter,因为若继承 SimpleChannelInboundHandler
 * 需要重写 channelRead0 方法 ,而重写该方法的目的是对来自于对方的数据进行处理,因为对方根本就没有发送数据,所以也就没有必要重写改方法
 * @author: xwy
 * @create: 2:17 PM 2020/8/6
 **/
public class SomeClientHandler extends SimpleChannelInboundHandler<String> {

    // msg 的消息类型为类中的泛型一致

    String message = "@description: SimpleChannelInboundHandler 和 ChannelInboundHandlerAdapter 的区别:\n" +
            " * SimpleChannelInboundHandler 中的 channelRead0 方法会自动释放接收到来自于对方 msg 的所占资源\n" +
            " * ChannelInboundHandlerAdapter 不会自动是否接受到来自于对方 msg 的所占资源\n" +
            " * 若对方没有向自己发送数据,则自定义处理器建议继承 ChannelInboundHandlerAdapter,因为若继承 SimpleChannelInboundHandler\n" +
            " * 需要重写 channelRead0 方法 ,而重写该方法的目的是对来自于对方的数据进行处理,因为对方根本就没有发送数据,所以也就没有必要重写改方法\n" +
            " * @author: xwy\n" +
            " * @create: 2:17 PM 2020/8/6";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " , " + msg);
        ctx.channel().writeAndFlush(message);
        ctx.channel().writeAndFlush(message);
//        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    // 当 Channel 被激活后触发该方法的执行
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("from client : begin talking... ");
        byte[] bytes = message.getBytes();
        ByteBuf buffer = null;
        for (int i = 0; i < 2; i++) {
            // 申请缓存空间
            buffer = Unpooled.buffer(bytes.length);
            // 将数据写入到缓存
            buffer.writeBytes(bytes);
            // 将缓存中的数据写入到 Channel
            ctx.writeAndFlush(buffer);
        }
    }

}