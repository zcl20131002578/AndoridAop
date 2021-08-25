package com.xuexuan.androidaop.patten;

import androidx.annotation.Nullable;

public class NewObject extends Object{

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


}
