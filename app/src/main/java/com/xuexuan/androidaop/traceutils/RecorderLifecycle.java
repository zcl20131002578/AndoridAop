package com.xuexuan.androidaop.traceutils;

interface RecorderLifecycle {
    public void onStart();
    public void onStop();
    public boolean isAlive();
}
