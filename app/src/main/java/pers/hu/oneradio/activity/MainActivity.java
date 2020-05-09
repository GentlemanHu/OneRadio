package pers.hu.oneradio.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.richpath.RichPathView;


import java.util.Random;
import java.util.concurrent.ExecutionException;

import pers.hu.oneradio.R;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.tool.hand.DjTask;
import pers.hu.oneradio.tool.music.MusicHelper;

public class MainActivity extends PerfectActivity {
    private Random random = new Random();
    private int homeCurrentItem = 5;
    private ItemIconAnimation itemIconAnimation;
    private SingleDetailDownloader downloader = new SingleDetailDownloader();
    private MusicHelper helper = new MusicHelper();
    private RichPathView commandRichPathView;
    private TextView textView;
    private Vibrator vibrator;
    private TimerTaskManager mTimerTask;
    private ImageView bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        textView = findViewById(R.id.songName);
        bg = findViewById(R.id.bg);

        commandRichPathView = findViewById(R.id.command);

        itemIconAnimation = new ItemIconAnimation(commandRichPathView);
        commandRichPathView.setOnPathClickListener((v) -> {
            vibe();
            update();
        });

        textView.setOnClickListener((v) -> {
            Intent home = new Intent(this, Home.class);
            home.putExtra("currentItem", homeCurrentItem);
            startActivity(home);
        });

    }

    private void update() {
        int[] ids = new int[]{794627365, 791601386, 618058, 793071394, 404005, 350052547, 338996078, 793339521};
        try {
            Toast.makeText(this, "开始请求数据", Toast.LENGTH_SHORT).show();
            DjTask djTask = new DjTask(this, ids[random.nextInt(ids.length)], textView, itemIconAnimation);
            djTask.execute();
        } catch (Exception e) {
            Toast.makeText(this, "eror错误没成功", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void vibe() {
        int strong_vibration = 30; //vibrate with a full power for 30
        long[] pattern = {
                0,  // Start immediately
                strong_vibration,
                50,
                strong_vibration
        };
        vibrator.vibrate(pattern, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("currentItemPosition", 0);
        homeCurrentItem = sp.getInt("currentItem",5);
        System.out.println("from back"+homeCurrentItem);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentItem",homeCurrentItem);
        editor.commit();
    }

}
