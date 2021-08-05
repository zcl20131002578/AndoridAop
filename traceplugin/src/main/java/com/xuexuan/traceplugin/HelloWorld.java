package com.xuexuan.traceplugin;

import java.io.FileOutputStream;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class HelloWorld extends ClassLoader implements Opcodes {

//    public static void main(final String args[]) throws Exception {
//
//
//        //定义一个叫做Example的类
//        ClassWriter cw = new ClassWriter(0);
//        cw.visit(V1_1, ACC_PUBLIC, "Example", null, "java/lang/Object", null);
//
//        //生成默认的构造方法
//        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V",
//                null,//表示是否是和泛型相关的
//                null);//这里无异常声明抛出， 传入null
//
//        //生成构造方法的字节码指令
//        /**
//         *  visitVarInsn 访问局部变量指令,局部变量指令是加载或存储局部变量值的指令
//         *
//         * 访问局部变量指令。 局部变量指令是加载或存储局部变量值的指令。
//         * 参数：opcode – 要访问的局部变量指令的操作码。 此操作码是 ILOAD、LLOAD、FLOAD、DLOAD、ALOAD、ISTORE、LSTORE、FSTORE、DSTORE、ASTORE 或 RET。
//         * var - 要访问的指令的操作数。 该操作数是局部变量的索引
//         *
//         * visitMethodInsn 访问方法指令，方法指令是调用方法的指令
//         * opcode          要访问的类型指令的操作码。
//         *                 此操作码是 INVOKEVIRTUAL、INVOKESPECIAL、INVOKESTATIC 或 INVOKEINTERFACE。
//         * owner           方法所有者类的内部名称（请参阅Type.getInternalName() ）。
//         * name            方法的名称。描述符 – 方法的描述符（请参阅Type ）。
//         * isInterface     如果方法的所有者类是接口。
//         * */
//        mw.visitVarInsn(ALOAD, 0);       //生成aload指令， 将第0个本地变量（也就是this）压入操作数栈
//        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
//        mw.visitInsn(RETURN);                //生成return指令， 方法返回。
//        /**
//         * 访问方法的最大堆栈大小和最大局部变量数
//         */
//        mw.visitMaxs(1, 1);//指定当前要生成的方法的最大局部变量和最大操作数栈。 对应Code属性中的max_stack和max_locals
//        /**
//         * 访问方法的结尾。 该方法是最后一个被调用的方法，用于通知访问者该方法的所有注解和属性都已被访问。
//         */
//        mw.visitEnd();//在此处表示当前要生成的构造方法已经创建完成
//
//        //生成main方法
//        mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
//                "main",
//                "([Ljava/lang/String;)V",
//                null,
//                null);
//
//        //生成main方法中的字节码指令
//        mw.visitFieldInsn(GETSTATIC, //访问类中的静态成员变量
//                "java/lang/System",
//                "out",
//                "Ljava/io/PrintStream;");
//
//        mw.visitLdcInsn("Hello world!");
//        mw.visitMethodInsn(INVOKEVIRTUAL,
//                "java/io/PrintStream",
//                "println",
//                "(Ljava/lang/String;)V", false);
//        mw.visitInsn(RETURN);
//        mw.visitMaxs(2, 2);
//
//        //字节码生成完成
//        mw.visitEnd();
//
//        // 获取生成的class文件对应的二进制流
//        byte[] code = cw.toByteArray();
//
//
//        //将二进制流写到本地磁盘上
//        FileOutputStream fos = new FileOutputStream("Example.class");
//        fos.write(code);
//        fos.close();
//
//        //直接将二进制流加载到内存中
//        HelloWorld loader = new HelloWorld();
//        Class<?> exampleClass = loader.defineClass("Example", code, 0, code.length);
//
//        //通过反射调用main方法
//        exampleClass.getMethods()[0].invoke(null, new Object[]{null});
//
//
//    }
}