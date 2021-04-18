package shengsiyuan.decorator;

/**
 * @description:
 * @author: xwy
 * @create: 上午9:20 2021/4/17
 **/

public class ConcreateDecorator1 extends Decorater {
    public ConcreateDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomeThing() {
        super.doSomeThing();
        this.doAnotherThing();
    }

    private void doAnotherThing() {
        System.out.println("功能 ConcreateDecorator1.doAnotherThing");
    }
}