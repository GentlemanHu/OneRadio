package pers.hu.oneradio.deal.music;

import android.os.AsyncTask;

import java.util.ArrayList;

//TODO:异步线程队列控制
public class AsyncTaskQueueManager {
    private static ArrayList<AsyncTask> taskQueue = new ArrayList<>();
    public static void add2TaskQueue(AsyncTask e){
        taskQueue.add(e);
    }
}
