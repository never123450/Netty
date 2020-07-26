package myTomcat.tomcat;

import myTomcat.servlet.CustomerHttpServlet;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: xwy
 * @create: 9:04 AM 2020/7/26
 **/

public class Registry {
    // 加载并缓存指定包中所有的servlet
    private List<String> servletNameCache = new ArrayList<>();

    // 注册servlet
    public void registerServlet(String basePackage, Map<String, Object> registerMap) throws Exception {
        // 对指定包中的servlet类名进行缓存
        cacheServletClass(basePackage);

        if (servletNameCache.size() == 0) {
            return;
        }

        for (String className : servletNameCache) {
            Class<?> clazz = Class.forName(className);
            Type superclass = clazz.getGenericSuperclass();
            if (superclass == CustomerHttpServlet.class) {
                // 获取到当前遍历server的全小写字母的简单类名
                String simpleClassName = className.substring(className.lastIndexOf(".") + 1).toLowerCase();
                registerMap.put(simpleClassName, clazz.newInstance());
            }
        }

    }

    private void cacheServletClass(String basePackage) {
        URL resource = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));

        if (resource == null) {
            return;
        }

        File dir = new File(resource.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                cacheServletClass(basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String fileName = file.getName().replace(".class", "").trim();
                servletNameCache.add(basePackage + "." + fileName);
            }
        }
    }


}