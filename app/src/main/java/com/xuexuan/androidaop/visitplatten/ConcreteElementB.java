package com.xuexuan.androidaop.visitplatten;

public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    protected void operation() {

    }
}
