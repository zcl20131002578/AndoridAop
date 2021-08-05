package com.xuexuan.traceplugin;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.commons.AdviceAdapter;

public class CustomAdviceAdapter extends AdviceAdapter {
    protected CustomAdviceAdapter(int i, MethodVisitor methodVisitor, int i1, String s, String s1) {
        super(i, methodVisitor, i1, s, s1);
    }
}
