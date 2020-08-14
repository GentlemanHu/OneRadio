package pers.hu.oneradio.activity.start;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import pers.hu.oneradio.OneRadioApplication;
import pers.hu.oneradio.activity.PerfectActivity;
import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.utils.ConfigChecker;


public class StartPage extends PerfectActivity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.hideStatusBar();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Intent it = new Intent(getApplicationContext(), Forward.class);
                    ConfigChecker checker = new ConfigChecker();
                    checker.checkConfig(StartPage.this);
                    // set global config
                    ((OneRadioApplication) getApplication()).setConfig(checker.getConfig());
                    startActivity(it);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 2600);
    }

}
