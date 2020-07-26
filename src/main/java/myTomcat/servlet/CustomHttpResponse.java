package myTomcat.servlet;


/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 8:31 AM 2020/7/26
**/

public interface CustomHttpResponse {

    /**
     * 将响应写入到channel
     * @param content
     */
    void write(String content) throws Exception;
}
