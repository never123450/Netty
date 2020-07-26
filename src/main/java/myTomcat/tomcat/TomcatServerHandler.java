package myTomcat.tomcat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import myTomcat.servlet.CustomerHttpServlet;

import java.util.Map;

/**
 * @description:
 * @author: xwy
 * @create: 9:28 AM 2020/7/26
 **/
// ChannelInboundHandlerAdapter 不会自动释放channelRead()方法中的msg参数
// SimpleChannelInboundHandler 会自动释放channelRead0()方法中的msg参数
public class TomcatServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> registerMap;

    public TomcatServerHandler(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            // 从请求中获取要范围到的servlet名称
            String servletName = request.uri().split("/")[1];
            CustomerHttpServlet servlet;
            // 若注册表中存在指定的servlet，则获取该servlet实现，否则获取默认的servlet
            if (registerMap.containsKey(servletName)) {
                servlet = (CustomerHttpServlet) registerMap.get(servletName);
            } else {
                servlet = new DefaultHttpServlet();
            }

            DefaultCustomHttpRequest req = new DefaultCustomHttpRequest(request);
            DefaultCustomHttpResponse res = new DefaultCustomHttpResponse(request, ctx);
            ctx.writeAndFlush(msg);
            if (request.method().name().equalsIgnoreCase("get")) {
                servlet.doGet(req, res);
            } else if (request.method().name().equalsIgnoreCase("post")) {
                servlet.doPost(req, res);
            }
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}