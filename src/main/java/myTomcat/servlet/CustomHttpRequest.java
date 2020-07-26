package myTomcat.servlet;

import java.util.List;
import java.util.Map;

/**
 *
 * @description: request规范
 *
 * @author: xwy
 *
 * @create: 8:28 AM 2020/7/26
**/

public interface CustomHttpRequest {
    /**
     * 获取请求URI
     * @return
     */
    String getUri();

    /**
     * 获取请求方式（GET POST等）
     * @return
     */
    String getMethod();

    /**
     * 获取请求参数
     * @return
     */
    Map<String,List<String>> getParameters();

    List<String> getParameters(String name);

    String getParameter(String name);

    /**
     * 获取请求路径，不包含请求参数的URI
     * @return
     */
    String getPath();
}
