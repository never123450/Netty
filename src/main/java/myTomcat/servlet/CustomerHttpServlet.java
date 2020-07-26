package myTomcat.servlet;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 8:33 AM 2020/7/26
**/

public abstract class CustomerHttpServlet {
    public abstract void doGet(CustomHttpRequest request,CustomHttpResponse response) throws  Exception;
    public abstract void doPost(CustomHttpRequest request,CustomHttpResponse response) throws  Exception;

}