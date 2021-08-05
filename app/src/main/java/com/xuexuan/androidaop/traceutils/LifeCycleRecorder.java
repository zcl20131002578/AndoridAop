package com.xuexuan.androidaop.traceutils;
import android.util.Log;
public class LifeCycleRecorder implements RecorderLifecycle {

    // application
    public static final String LIFECYCLE_ATTACH_BASE_CONTEXT = "attachBaseContext";
    // activity
    public static final String LIFECYCLE_CREATE = "onCreate";
    public static final String LIFECYCLE_START = "onStart";
    public static final String LIFECYCLE_RESTART = "onRestart";
    public static final String LIFECYCLE_RESUME = "onResume";
    public static final String LIFECYCLE_PAUSE = "onPause";
    public static final String LIFECYCLE_STOP = "onStop";
    public static final String LIFECYCLE_DESTROY = "onDestroy";
    // service
    public static final String LIFECYCLE_START_COMMAND = "onStartCommand";
    public static final String LIFECYCLE_BIND = "onBind";
    public static final String LIFECYCLE_UNBIND = "onUnbind";
    public static final String LIFECYCLE_REBIND = "onRebind";
    // receive
    public static final String LIFECYCLE_RECEIVE = "onReceive";

    public static final int LIFECYCLE_TYPE_APPLICATION = 1;
    public static final int LIFECYCLE_TYPE_ACTIVITY = 2;
    public static final int LIFECYCLE_TYPE_SERVICE = 3;
    public static final int LIFECYCLE_TYPE_RECEIVER = 4;

    public static int TEST_INT = 0;
    public static String TEST_INT_STRING = "TEST_INT_STRING";

    public static long start() {
//        return SystemClock.uptimeMillis();
        Log.e("ZCLZCL_PERF", "LifeCycleRecorder: start");
        return System.currentTimeMillis();
    }

    public static void end(int type, String componentName, String lifeCycleName, long startTime) {
        Log.e("ZCLZCL_PERF", "LifeCycleRecorder: end startTime: " + startTime + " type: " + type
                + " componentName: " + componentName + " lifeCycleName: " + lifeCycleName);
//        LifeCycleHandleTask handleTask = new LifeCycleHandleTask(type, componentName, lifeCycleName, SystemClock.uptimeMillis() - startTime);
//        IssueReporter.getDetectHandler().post(handleTask);
    }

    @Override

    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
