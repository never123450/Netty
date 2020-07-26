package myTomcat.tomcat;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import myTomcat.servlet.CustomHttpRequest;

import java.util.List;
import java.util.Map;

/**
 *
 * @description: 
 *
 * @author: xwy
 *
 * @create: 8:35 AM 2020/7/26
**/

public class DefaultCustomHttpRequest implements CustomHttpRequest {

    private HttpRequest request;

    public DefaultCustomHttpRequest(HttpRequest request) {
        this.request = request;
    }

    @Override
    public String getUri() {
        return request.uri();
    }

    @Override
    public String getMethod() {
        return request.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return new QueryStringDecoder(request.uri()).parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return this.getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> parameters = this.getParameters(name);
        if (parameters == null || parameters.size()==0)
            return null;
        return parameters.get(0);
    }

    @Override
    public String getPath() {
        return new QueryStringDecoder(request.uri()).path();
    }
}