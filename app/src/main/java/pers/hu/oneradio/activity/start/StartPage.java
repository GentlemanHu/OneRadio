package pers.hu.oneradio.activity.start;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import pers.hu.oneradio.activity.PerfectActivity;


public class StartPage extends PerfectActivity {
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hideStatusBar();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    Intent it = new Intent(getApplicationContext(), Forward.class);
                    startActivity(it);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable,2600);
    }

}
