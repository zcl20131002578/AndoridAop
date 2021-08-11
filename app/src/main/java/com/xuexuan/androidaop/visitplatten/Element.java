package com.xuexuan.androidaop.visitplatten;

//稳定的类，不希望在此类增加逻辑代码，且不能通过该类的实例对象直接调用
public interface Element {
    //accept the instance of class which visit the class
    void accept(Visitor visitor);
}
