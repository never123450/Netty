package webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
 * @create: 2020-08-09 17:21:24
 **/

public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String text = ((TextWebSocketFrame) msg).text();
        ctx.channel().writeAndFlush(new TextWebSocketFrame("from client: + " + text));
    }
}