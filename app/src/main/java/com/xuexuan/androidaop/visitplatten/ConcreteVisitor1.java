package com.xuexuan.androidaop.visitplatten;

public class ConcreteVisitor1 implements Visitor{
    @Override
    public void visit(ConcreteElementA a) {
        a.operation();
    }

    @Override
    public void visit(ConcreteElementB b) {
        b.operation();
    }
}
