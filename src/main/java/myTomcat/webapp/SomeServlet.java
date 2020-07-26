package myTomcat.webapp;

import myTomcat.servlet.CustomHttpRequest;
import myTomcat.servlet.CustomHttpResponse;
import myTomcat.servlet.CustomerHttpServlet;

/**
 * @description:
 * @author: xwy
 * @create: 8:57 AM 2020/7/26
 **/

public class SomeServlet extends CustomerHttpServlet {

    @Override
    public void doGet(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        String parameter = request.getParameter("name");
        String uri = request.getUri();
        String path = request.getPath();
        String method = request.getMethod();
        String content = "method = " + method + "\n" +
                "uri = " + uri + "\n" +
                "path = " + path + "\n" +
                "param = " + parameter;
        response.write(content);
    }

    @Override
    public void doPost(CustomHttpRequest request, CustomHttpResponse response) throws Exception {
        doGet(request, response);
    }
}