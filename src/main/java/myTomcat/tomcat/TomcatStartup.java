package myTomcat.tomcat;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @description:
 *
 * @author: xwy
 *
 * @create: 9:53 AM 2020/7/26
**/

/*
访问：
http://localhost:8888/otherservlet/abd/def?name=xxx

显示：
method = GET
uri = /otherservlet/abd/def?name=xxx
path = /otherservlet/abd/def
param = xxx

 */
public class TomcatStartup {
    public static void main(String[] args) throws Exception {
        Map<String,Object> registerMap = new HashMap<>();
        new Registry().registerServlet("myTomcat.webapp",registerMap);
        TomcatServer tomcatServer = new TomcatServer(registerMap);
        tomcatServer.start();
    }
}