package pers.hu.oneradio.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class StartPage extends PerfectActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(3200);//使程序休眠一秒
                    Intent it = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }

}
