package com.xuexuan.traceplugin;

import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;

import java.util.Set;

public class MyTransform extends Transform {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return null;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return null;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }
}
