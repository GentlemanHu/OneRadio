package pers.hu.oneradio.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import pers.hu.oneradio.R;

public class StartPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(1500);//使程序休眠一秒
                    Intent it = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();//关闭当前活动
                    Transition.runFadeInAnimation(findViewById(R.id.angry_btn));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
