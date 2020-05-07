package pers.hu.oneradio;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startMainActivity();

    }

    private void startMainActivity(){
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,MainPage.class);
                startActivity(mainIntent);
                MainActivity.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,2000);//延时两秒执行 run 里面的操作
    }

}
