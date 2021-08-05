package com.xuexuan.traceplugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * 对继承自AppCompatActivity的Activity进行插桩
 */

public class TraceVisitor extends ClassVisitor {

    /**
     * 类名
     */
    private String className;

    /**
     * 父类名
     */
    private String superName;

    /**
     * 该类实现的接口
     */
    private String[] interfaces;

    public TraceVisitor(String className, ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
        this.className = className;
    }

    /**
     * ASM进入到类的方法时进行回调
     *
     * @param access
     * @param name       方法名
     * @param desc
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     String[] exceptions) {


        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        methodVisitor = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {

            private boolean isInject() {
                //如果父类名是AppCompatActivity则拦截这个方法,实际应用中可以换成自己的父类例如BaseActivity
                if (superName.contains("AppCompatActivity")) {
                    return true;
                }
                return false;
            }

            @Override
            public void visitCode() {
                super.visitCode();
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                super.visitFieldInsn(opcode, owner, name, desc);
            }


            /**
             * 方法开始之前回调
             */
            @Override
            protected void onMethodEnter() {
                if (isInject()) {
                    if ("onCreate".equals(name)) {
                        /*
                         *  visitVarInsn 访问局部变量指令,局部变量指令是加载或存储局部变量值的指令
                         *
                         * 访问局部变量指令。 局部变量指令是加载或存储局部变量值的指令。
                         * 参数：opcode – 要访问的局部变量指令的操作码。 此操作码是 ILOAD、LLOAD、FLOAD、DLOAD、ALOAD、ISTORE、LSTORE、FSTORE、DSTORE、ASTORE 或 RET。
                         * var - 要访问的指令的操作数。 该操作数是局部变量的索引
                         *
                         * visitMethodInsn 访问方法指令，方法指令是调用方法的指令
                         * opcode          要访问的类型指令的操作码。
                         *                 此操作码是 INVOKEVIRTUAL、INVOKESPECIAL、INVOKESTATIC 或 INVOKEINTERFACE。
                         * owner           方法所有者类的内部名称（请参阅Type.getInternalName() ）。
                         * name            方法的名称。描述符 – 方法的描述符（请参阅Type ）。
                         * isInterface     如果方法的所有者类是接口。
                         *
                         *
                         * Opcodes.INVOKEVIRTUAL 指的是调用实例方法。
                         * */
                        invokeCreateMethod();

                        lifeOnEnd(lifeOnCreate());

                        lifeOnEnd(lifeOnCreate());

                    } else if ("onDestroy".equals(name)) {
                        trackOnDestroyMethod();
                    }
                }
            }

            private int lifeOnCreate() {
                mv.visitMethodInsn(INVOKESTATIC, "com/xuexuan/androidaop/traceutils/LifeCycleRecorder", "start", "()J", false);
                int localIndex = newLocal(Type.LONG_TYPE);
                mv.visitVarInsn(LSTORE, localIndex);
                return localIndex;
            }

            private void lifeOnEnd(int localIndex) {
                mv.visitInsn(ICONST_1);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getName", "()Ljava/lang/String;", false);
                mv.visitLdcInsn("onCreate");
                mv.visitVarInsn(LLOAD, localIndex);
                mv.visitMethodInsn(INVOKESTATIC, "com/xuexuan/androidaop/traceutils/LifeCycleRecorder", "end", "(ILjava/lang/String;Ljava/lang/String;J)V", false);
            }



            private void trackOnDestroyMethod() {
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESTATIC, "com/xuexuan/androidaop/traceutils/TraceUtil"
                        , "onActivityDestroy", "(Landroid/app/Activity;)V", false);
            }

            private void invokeCreateMethod() {
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESTATIC,
                        "com/xuexuan/androidaop/traceutils/TraceUtil",
                        "onActivityCreate", "(Landroid/app/Activity;)V",
                        false);
            }

            /**
             * 方法结束时回调
             * @param opcode
             */
            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
            }
        };
        return methodVisitor;

    }

    /**
     * 当ASM进入类时回调
     *
     * @param version
     * @param access
     * @param name       类名
     * @param signature
     * @param superName  父类名
     * @param interfaces 实现的接口名
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
        this.interfaces = interfaces;
    }
}
