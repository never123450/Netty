package myTomcat.tomcat;

import myTomcat.servlet.CustomHttpRequest;
import myTomcat.servlet.CustomHttpResponse;
import myTomcat.servlet.CustomerHttpServlet;

/**
 *
 * @description: 当找不到客户端所访问的servlet时会调用该默认servlet
 *
 * @author: xwy
 *
 * @create: 8:56 AM 2020/7/26
**/

public class DefaultHttpServlet extends CustomerHttpServlet {

    @Override
    public void doGet(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        // 获取到servlet的名称
        String servletName = request.getUri().split("/")[0];
        // 构建一个异常信息
        String content = "404 - no this servlet: " + servletName;
        // 将异常信息返回给客户端
        response.write(content);
    }

    @Override
    public void doPost(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        doGet(request,response);
    }
}