package shengsiyuan.decorator;

/**
 * @description:
 * @author: xwy
 * @create: 上午9:18 2021/4/17
 **/

public class Decorater implements Component {

    private Component component;

    public Decorater(Component component) {
        this.component = component;
    }

    @Override
    public void doSomeThing() {
        component.doSomeThing();
    }
}
