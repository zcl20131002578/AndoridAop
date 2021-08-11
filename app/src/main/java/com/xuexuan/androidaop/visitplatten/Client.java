package com.xuexuan.androidaop.visitplatten;

public class Client {
    ObjectStructure structure= new ObjectStructure();
    Visitor visitor1;
    Visitor visitor2;




    public void walk1(Visitor visitor1) {
        for (Element element : structure) {
            element.accept(visitor1);
            /**
             * 1:稳定的被参观类通过accept方式接受参观类对象
             * 2:参观类通过visit方法将稳定类的对象this作为参数传入
             * 3:参观类通过传入的稳定类对象调用稳定类方法
             */
        }


    }

    public void walk2(Visitor visitor2) {
        for (Element element : structure) {
            element.accept(visitor2);
        }
    }
}
