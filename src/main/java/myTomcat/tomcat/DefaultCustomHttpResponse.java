package myTomcat.tomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;
import myTomcat.servlet.CustomHttpResponse;


/**
 * @description:
 * @author: xwy
 * @create: 8:40 AM 2020/7/26
 **/

public class DefaultCustomHttpResponse implements CustomHttpResponse {

    private HttpRequest request;
    private ChannelHandlerContext context;


    public DefaultCustomHttpResponse(HttpRequest request, ChannelHandlerContext context) {
        this.request = request;
        this.context = context;
    }

    @Override
    public void write(String content) throws Exception {
        if (StringUtil.isNullOrEmpty(content)) {
            return;
        }

        // 创建响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                // 根据响应体内容为response实例分配空间
                Unpooled.wrappedBuffer(content.getBytes("UTF-8"))
        );

        // 获取响应头
        HttpHeaders headers = response.headers();
        // 设置响应内容类型
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
        // 设置响应内容长度
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        // 设置缓存过期时长
        headers.set(HttpHeaderNames.EXPIRES,0);
        // 若请求使用的是长连接，则设置响应连接也为长连接
        if (HttpUtil.isKeepAlive(request)) {
            headers.set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }

        // 将响应写入到channel
        context.writeAndFlush(response);
    }
}