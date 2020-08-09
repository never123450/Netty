package demo.nettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @description: 自定义服务端处理器
 * 需求:用户提交一个请求后,在浏览器上就会看到 hello netty world
 * @author: xwy
 * @create: 1:04 PM 2020/8/6
 **/

public class ServerHandler extends ChannelInboundHandlerAdapter {

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelActive");
//    }
//
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("channelRegistered");
//    }
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("handlerAdded");
//    }


    /**
     * 当 channel 中有来自于客户端的数据时就会触发该方法的执行
     *
     * @param ctx 上下文对象
     * @param msg 来自于客户端的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg = " + msg.getClass());
        System.out.println("客户端地址 = " + ctx.channel().remoteAddress());
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("请求方式: " + request.method().name());
            System.out.println("请求 uri: " + request.uri());

            if ("/favicon.ico".equalsIgnoreCase(request.uri())){
                System.out.println("不处理 /favicon.ico 请求");
                return;
            }
            // 构造 response 的响应体
            ByteBuf body = Unpooled.copiedBuffer("hello netty world", CharsetUtil.UTF_8);
            // 生成响应对象
            DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
            // 获取到 response 的头部进行初始化
            HttpHeaders headers = defaultFullHttpResponse.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

            // 将响应对象写入到 Channel
            ctx.writeAndFlush(defaultFullHttpResponse)
                    // 添加监听器,响应体发送完毕则直接将 channel 关闭
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 当 Channel 中的数据在处理的过程中出现异常时会触发该方法的执行
     *
     * @param ctx   上下文
     * @param cause 发生异常的对象
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭 Channel
        ctx.close();
    }
}