package pers.hu.oneradio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.view.View;

import com.richpath.RichPathView;

import androidx.appcompat.app.AppCompatActivity;

import pers.hu.oneradio.R;
import pers.hu.oneradio.view.home.animation.ItemIconAnimation;

public class MainActivity extends Activity {
    private ItemIconAnimation itemIconAnimation = new ItemIconAnimation();
    private RichPathView commandRichPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        commandRichPathView = findViewById(R.id.command);

        commandRichPathView.setOnPathClickListener((v) -> {
            itemIconAnimation.animateCommand(commandRichPathView);
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    
}
