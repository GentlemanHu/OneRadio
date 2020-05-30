package pers.hu.oneradio.activity.start;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lzx.starrysky.utils.TimerTaskManager;
import com.richpath.RichPathView;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import pers.hu.oneradio.R;
import pers.hu.oneradio.activity.PerfectActivity;
import pers.hu.oneradio.activity.home.Home;
import pers.hu.oneradio.deal.hand.async.GetDjIdTask;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.deal.hand.async.DjTask;
import pers.hu.oneradio.deal.music.MusicHelper;
import pers.hu.oneradio.net.model.DjBoardEnum;

public class Forward extends PerfectActivity {
    private Random random = new Random();
    private ItemIconAnimation itemIconAnimation;
    private RichPathView commandRichPathView;
    private TextView textView;
    private Vibrator vibrator;
    private Handler forward;
    private Intent home;
    private ImageView bg;
    private Integer[] ids;

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
            Toast.makeText(this, "点着舒服吗~~", Toast.LENGTH_SHORT).show();
            //update();
        });
        home = new Intent(this, Home.class);

        textView.setOnClickListener((v) -> {
            Toast.makeText(this, "在努力请求数据，再等等嘛，就一下下~~", Toast.LENGTH_SHORT).show();
        });

        forward = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        onReceiveData(msg);
                        break;
                    default:
                        break;
                }
            }
        };

        update();
    }

    private void update() {
        try {
            Toast.makeText(this, "开始请求电台列表数据~", Toast.LENGTH_SHORT).show();

            GetDjIdTask task = new GetDjIdTask(forward);
            task.execute(DjBoardEnum.getRandomDjEnum());

            //TODO:HOME图片缓存队列，可以直接呈现，不用在第二页等待

        } catch (Exception e) {
            Toast.makeText(this, "网络超时或者其他错误，请重试一下喽~", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void onReceiveData(Message msg) {
        ids = (Integer[]) msg.obj;
        Toast.makeText(Forward.this, "数据加载完成", Toast.LENGTH_LONG).show();
        DjTask djTask = new DjTask(Forward.this, ids[random.nextInt(ids.length)], textView, itemIconAnimation);
        djTask.execute();

        Runnable jump = new Runnable() {
            @Override
            public void run() {
                //TODO：反复转换数据类型，需要重构
                int[] intArray = Arrays.stream(ids).mapToInt(Integer::intValue).toArray();
                home.putExtra("ids", intArray);
                startActivity(home);
                finish();
            }
        };
        forward.postDelayed(jump, 3000);
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


}
