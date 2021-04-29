package pers.hu.oneradio.base;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static final Executor executor = Executors.newFixedThreadPool(5);
    private static final Looper looper = Looper.getMainLooper();
    private static final Handler handler = new Handler(looper);

    public static void runInBackground(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runInMainThread(Runnable runnable) {
        handler.post(runnable);
    }
}
