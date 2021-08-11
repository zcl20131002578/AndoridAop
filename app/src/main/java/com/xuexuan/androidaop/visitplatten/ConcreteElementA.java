package com.xuexuan.androidaop.visitplatten;


public class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }


    protected void operation() {

    }
}
