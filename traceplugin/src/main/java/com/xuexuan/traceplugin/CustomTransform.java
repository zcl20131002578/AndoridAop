package com.xuexuan.traceplugin;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Status;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CustomTransform extends Transform {
    public static final String TAG = "CustomTransform";

    public CustomTransform() {
        super();
    }

    @Override
    public String getName() {
        return "CustomTransform";
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException, IOException {
//        super.transform(transformInvocation);
//        //当前是否是增量编译
//        boolean isIncremental = transformInvocation.isIncremental();
//        //消费型输入，可以从中获取jar包和class文件夹路径。需要输出给下一个任务
//        Collection<TransformInput> inputs = transformInvocation.getInputs();
//        //引用型输入，无需输出。
//        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs();
//        //OutputProvider管理输出路径，如果消费型输入为空，你会发现 OutputProvider = null
//        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
//        for(TransformInput input : inputs) {
//            for(JarInput jarInput : input.getJarInputs()) {
//                File dest = outputProvider.getContentLocation(
//                        jarInput.getFile().getAbsolutePath(),
//                        jarInput.getContentTypes(),
//                        jarInput.getScopes(),
//                        Format.JAR);
//                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyFile(jarInput.getFile(), dest);
//            }
//            for(DirectoryInput directoryInput : input.getDirectoryInputs()) {
//                File dest = outputProvider.getContentLocation(
//                        directoryInput.getName(),
//                        directoryInput.getContentTypes(),
//                        directoryInput.getScopes(),
//                        Format.DIRECTORY);
//                //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
//                FileUtils.copyDirectory(directoryInput.getFile(), dest);
//            }
//        }
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        boolean isIncremental = transformInvocation.isIncremental();
        boolean emptyRun = false;
        //如果非增量，则清空旧的输出内容
        if (!isIncremental) {
            outputProvider.deleteAll();
        }
        for (TransformInput input : inputs) {
            //对jar包类型进行修改
            for (JarInput jarInput : input.getJarInputs()) {
                Status status = jarInput.getStatus();
                File dest = outputProvider.getContentLocation(jarInput.getName(), jarInput.getContentTypes(),
                        jarInput.getScopes(), Format.JAR);
                if (isIncremental && !emptyRun) {
                    switch (status) {
                        case NOTCHANGED://当前文件不需处理，甚至复制操作都不用
                            continue;
                        case ADDED://正常处理，输出给下一个任务
                        case CHANGED:
                            transformJar(jarInput.getFile(), dest, status);
                            break;
                        case REMOVED://移除outputProvider获取路径对应的文件
                            if (dest.exists()) {
                                FileUtils.forceDelete(dest);
                            }
                            break;
                    }
                } else {
                    transformJar(jarInput.getFile(), dest, status);
                }
            }
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                //对class类型进行修改
                File dest = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                FileUtils.forceMkdir(dest);
                if (isIncremental && !emptyRun) {
                    String srcDirPath = directoryInput.getFile().getAbsolutePath();
                    String destDirPath = dest.getAbsolutePath();
                    //可以直接拿到更改的文件
                    Map<File, Status> fileStatusMap = directoryInput.getChangedFiles();
                    for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                        Status status = changedFile.getValue();
                        File inputFile = changedFile.getKey();
                        String destFilePath = inputFile.getAbsolutePath().replace(srcDirPath, destDirPath);
                        File destFile = new File(destFilePath);
                        switch (status) {
                            case NOTCHANGED:
                                break;
                            case REMOVED:
                                if (destFile.exists()) {
                                    FileUtils.forceDelete(destFile);
                                }
                                break;
                            case ADDED:
                            case CHANGED:
                                FileUtils.touch(destFile);
                                transformSingleFile(inputFile, destFile, srcDirPath);
                                break;
                        }
                    }
                } else {
                    transformDir(directoryInput.getFile(), dest);
                }
            }
        }

    }

    private void transformDir(File file, File dest) {
    }

    private void transformSingleFile(File inputFile, File destFile, String srcDirPath) {
    }

    private void transformJar(File file, File dest, Status status) {

    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public Set<QualifiedContent.ContentType> getOutputTypes() {
        return super.getOutputTypes();
    }

    @Override
    public Set<? super QualifiedContent.Scope> getReferencedScopes() {
        return TransformManager.EMPTY_SCOPES;
    }

    @Override
    public Map<String, Object> getParameterInputs() {
        return super.getParameterInputs();
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public boolean isIncremental() {
        return true; //是否开启增量编译
    }
}
