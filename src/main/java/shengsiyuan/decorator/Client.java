package shengsiyuan.decorator;

/**
 * @description:
 * @author: xwy
 * @create: 上午9:15 2021/4/17
 **/

public class Client {
    public static void main(String[] args) {
        Component component = new ConcreateDecorator2(new ConcreateDecorator1(new ConcreteComponent()));
        component.doSomeThing();
    }
}