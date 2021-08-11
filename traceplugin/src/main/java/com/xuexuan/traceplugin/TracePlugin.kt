package com.xuexuan.traceplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * 为了让我们的groovy/kotlin类申明为gradle的插件，新建的groovy/kotlin需要实现org.gradle.api.Plugin接口
 *
 * 在META-INF里面新建gradle-plugins目录,XXX.properties. 使用时即apply plugin:'XXX'。
 * 在里面指明自定义插件的类 例如: implementation-class=com.xuexuan.traceplugin.TracePlugin
 *
 * 第一部分:如何将一个module配置成一个gradle插件，并上传本地/云端
 * 第二部分:如何使用
 * （1）maven仓库依赖
 * （2）classpath GAV依赖
 * （3）apply plugin 例如: apply plugin: 'xuexuan.github.com.traceplugin'
 */
class TracePlugin :  Plugin<Project> {


    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)
        android.registerTransform(LogTransform())
    }

}