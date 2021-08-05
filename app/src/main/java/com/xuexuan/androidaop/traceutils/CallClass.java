package com.xuexuan.androidaop.traceutils;

import android.os.SystemClock;
import android.util.Log;

public class CallClass {

    public final static int TEST_INT = 0;
    public final static String TEST_STRING = "11111";
    public static String TEST_INT_STRING = "TEST_INT_STRING";

    public void mainMethod() {
        long time = LifeCycleRecorder.start();//2
        LifeCycleRecorder.end(TEST_INT, this.getClass().getName(), TEST_INT_STRING, time);
    }

}
