package shengsiyuan.decorator;

/**
 * @description:
 * @author: xwy
 * @create: 上午9:22 2021/4/17
 **/

public class ConcreateDecorator2 extends Decorater {
    public ConcreateDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomeThing() {
        super.doSomeThing();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("功能 ConcreateDecorator2.doAnotherThing");
    }
}