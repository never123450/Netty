package shengsiyuan.decorator;

/**
 * @description:
 * @author: xwy
 * @create: 上午9:15 2021/4/17
 **/

public class ConcreteComponent implements Component {
    @Override
    public void doSomeThing() {
        System.out.println("功能 ConcreteComponent");
    }
}