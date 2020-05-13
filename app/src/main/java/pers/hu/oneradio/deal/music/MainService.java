package pers.hu.oneradio.deal.music;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

@SuppressWarnings("测试Service")
public class MainService extends Service {
    public class MyBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }
    }

    private MyBinder binder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("binder好了");
        return binder;
    }

    public MainService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startOk();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("service销毁");
        return super.onUnbind(intent);
    }

    public void startOk(){
        System.out.println("service 已启动");
    }
}

