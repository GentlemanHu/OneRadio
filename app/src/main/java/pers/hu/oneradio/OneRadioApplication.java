package pers.hu.oneradio;

import android.app.Application;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySkyConfig;

public class OneRadioApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StarrySky.Companion.init(this);
    }
}
