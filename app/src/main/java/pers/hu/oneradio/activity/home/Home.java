package pers.hu.oneradio.activity.home;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.exoplayer2.source.UnrecognizedInputFormatException;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.provider.SongInfo;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.richpath.RichPathView;
import com.tmall.ultraviewpager.UltraViewPagerAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.R;
import pers.hu.oneradio.activity.PerfectActivity;
import pers.hu.oneradio.deal.hand.async.WaitSongReadyTask;
import pers.hu.oneradio.deal.listener.OnDataLoadCompleted;
import pers.hu.oneradio.deal.listener.OnTaskCompleted;
import pers.hu.oneradio.deal.hand.handler.DjIdHandler;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.feel.home.animation.Transition;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.feel.home.perfectview.CustPagerTransformer;
import pers.hu.oneradio.deal.music.SongListManager;
import pers.hu.oneradio.feel.home.perfectview.PerfectViewPager;
import pers.hu.oneradio.feel.player.Visualizer;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.deal.hand.PerfectPagerAdapter;
import pers.hu.oneradio.deal.hand.handler.DetailHandler;
import pers.hu.oneradio.deal.hand.handler.PictureTaskHandler;
import pers.hu.oneradio.net.model.Program;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.Utils;

public class Home extends PerfectActivity implements OnTaskCompleted, OnDataLoadCompleted {
    private ItemIconAnimation animation;
    private int last_position;
    private final OnTaskCompleted dealer = this;
    private RichPathView load;
    private static int position = 0;
    private Integer list_position = 0;
    private TextView djName;
    private ImageView homebg;
    private final Random random = new Random();
    private RelativeLayout homelayout;
    private Context home;
    private Visualizer visualizer;
    private BlastVisualizer blast;
    private SpinKitView musicView;
    private DjDetail dj;
    private ArrayList<SongInfo> songInfos;
    private PerfectPagerAdapter adapter;
    private final ArrayList<CommonFragment> fragments = new ArrayList<>();
    private PerfectViewPager viewPager;
    private Integer[] ids;
    private BoomMenuButton boom;
    private Handler handler;
    private Handler detailHandler, pictureHandler, djIdHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(getMainLooper());

        home = this.getApplicationContext();
        setContentView(R.layout.home);

        homebg = findViewById(R.id.homebg);
        djName = findViewById(R.id.djname);
        musicView = findViewById(R.id.spin_kit);
        boom = findViewById(R.id.boom);
        load = findViewById(R.id.loading);
        homelayout = findViewById(R.id.homelayout);
        viewPager = findViewById(R.id.viewpager);
        blast = findViewById(R.id.blast);

        visualizer = Visualizer.getVisulizer(blast);
        animation = new ItemIconAnimation(load);
        detailHandler = new DetailHandler();
        djIdHandler = new DjIdHandler();

        musicView.setVisibility(View.INVISIBLE);

        StarrySky.Companion.with().addPlayerEventListener(getMusicListener());
        animation.animateCommand();
        boomInit();

        init();
        //new showMessageAsync().execute();

    }

    //初始化，采用handler异步延迟启动，防止阻塞主线程
    private void init() {
        ids = Arrays.stream(getIntent().getIntArrayExtra("ids")).boxed().toArray(Integer[]::new);
        adapter = new PerfectPagerAdapter(getSupportFragmentManager(), ids);
        viewPager.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                fillViewPager();
                handler.sendEmptyMessage(1);
            }
        };
        thread.start();
    }

    private void fillViewPager() {
        load.setVisibility(View.INVISIBLE);

        adapter.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(7);
        viewPager.setDefaultTransformer();
        //动态添加page
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Home.position = position;
                load.setVisibility(View.VISIBLE);
                //稍微延迟请求，防止阻塞，减少卡顿感
                if (last_position < position) {
                    Runnable add = new Runnable() {
                        @Override
                        public void run() {
                            adapter.addItem();
                        }
                    };
                    handler.postDelayed(add, 100);
                }
                //加入判断，防止每次触发异步任务
                if (SongListManager.getDjDetails().size() - 2 >= position) {
                    new Thread() {
                        @Override
                        public void run() {
                            blurImage(position);
                            getSongs(position);
                        }
                    }.start();
                } else {
                    new WaitSongReadyTask(dealer, position).execute();
                    Toast toast = Toast.makeText(Home.this, "请等待电台信息加载完毕~~", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                }

                last_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getSongs(int position) {
        dj = SongListManager.getDjDetails().get(position);

        djName.startAnimation(Transition.textSlide(this));
        if (dj.getRcmdtext() == null)
            djName.setText("同一个世界 同一个电台\n  --OneRadio");
        else if (dj.getRcmdtext() != null)
            djName.setText(dj.getRcmdtext());
        songInfos = SongListManager.getSongPlaylist(position);

        try {
            StarrySky.Companion.with().addPlayList(songInfos);
            StarrySky.Companion.with().playMusic(songInfos, 0);
            System.out.println(songInfos.get(0).getSongName() + ">>>>>>>>>>>>>电台");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Home.this, "欢迎收听<" + dj.getName() + ">" + "电台~", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage() + "错误信息！！！");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Home.this, "发生未知错误，请重试或切换其他电台~~", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void blurImage(int position) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable d = adapter.getFragments().get(position).getImageView().getDrawable();
                    if (d != null) {
                        load.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = Utils.drawableToBitmap(d);

                        homelayout.setBackground(d);
                        homebg.startAnimation(Transition.textSlide(Home.this));
                        homebg.setImageBitmap(bitmap);
                        Blurry.with(Home.this).animate(21000).sampling(2).from(bitmap).into(homebg);

                        int color = Utils.getDominantColor(bitmap);

                        musicView.setAnimation(Transition.textSlide(Home.this));
                        musicView.setColor(color/3);
                        System.out.println(Utils.getDominantColor(bitmap) + "get main color");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 150);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void boomInit() {
        //TODO:详细设计
        boom.setButtonEnum(ButtonEnum.Ham);
        boom.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        boom.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);

        for (int i = 0; i < boom.getButtonPlaceEnum().buttonNumber(); i++) {
            boom.addBuilder(new HamButton.Builder().listener(new OnBMClickListener() {
                @Override
                public void onBoomButtonClick(int index) {
                    Toast.makeText(Home.this, "牛哔哔哔", Toast.LENGTH_LONG).show();
                }
            })
                    .normalImageRes(R.drawable.command));
        }

        boom.setHideDuration(300);
        boom.setShowDuration(700);
        boom.setOnClickListener((v) -> {
            System.out.println("clicked!!!");
        });
    }

    @Override
    public void onTaskCompleted(int position) {
        blurImage(position);
        getSongs(position);
    }

    @Override
    public int getCurrentPosition() {
        return position;
    }



    private OnPlayerEventListener getMusicListener() {
        OnPlayerEventListener listener = new OnPlayerEventListener() {
            @Override
            public void onMusicSwitch(SongInfo songInfo) {
                musicView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPlayerStart() {
                musicView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPlayerPause() {
                musicView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPlayerStop() {
                musicView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPlayCompletion(SongInfo songInfo) {

            }

            @Override
            public void onBuffering() {

            }

            @Override
            public void onError(int i, String s) {

            }
        };
        return listener;
    }
    @Deprecated
    @Override
    //在Async中处理
    public void onDataLoadCompleted(DjDetail dj) {
        //防止并发同时操作
        synchronized (list_position) {
            songInfos = new ArrayList<>();
            Program[] songs = dj.getPrograms();

            for (Program s : songs) {
                Song song = s.getMainSong();
                SongInfo songInfo = new SongInfo();
                songInfo.setSongUrl(song.getUrl());
                songInfo.setSongId(s.getMainSong().getId().toString());
                songInfo.setSongName(dj.getName());
                songInfo.setArtist(dj.getRcmdtext());
                songInfo.setSongCover(dj.getPicUrl());
                System.out.println(song.getUrl());
                songInfos.add(songInfo);
            }
            SongListManager.addPlayList(list_position, songInfos);
            SongListManager.addDjDetail(dj);
            this.list_position++;
        }
    }

    interface DoTime {
        void doTime();
    }

    private void requestPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasAudioPermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            int hasInternetPermission = checkSelfPermission(Manifest.permission.INTERNET);
            List<String> permissions = new ArrayList<>();
            if (hasAudioPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (hasInternetPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.INTERNET);
            }
            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 1);
            }
        }
    }

    //暂时适用的提示类
    public class showMessageAsync extends AsyncTask<String, Void, String> implements DoTime {
        AlertDialog alertDialog;

        @Override
        protected String doInBackground(String... strings) {
            final Runnable init = new Runnable() {
                @Override
                public void run() {
                    //"TODO：反复转换数据类型，需要重构"
                    //接受预加载的ids，并转换为Integer[]

                }
            };

            handler.postDelayed(init, 50);
            return "ok";
        }


        protected void onPreExecute() {
            super.onPreExecute();
            Home.this.runOnUiThread(new Thread(() -> {
                alertDialog = new AlertDialog.Builder(Home.this).create();
                alertDialog.setTitle("坐下，喝茶~");
                alertDialog.setIcon(R.drawable.command);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage(Html.fromHtml(getString(R.string.update_message), Html.FROM_HTML_MODE_COMPACT));

                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                int countdown = 4;
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, 7 + "s", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        final Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                        button.setEnabled(false);
                        new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                button.setText(millisUntilFinished / 1000 + "s");
                            }

                            @Override
                            public void onFinish() {
                                button.setText("OK");
                                button.setEnabled(true);
                            }
                        }.start();
                    }
                });
                alertDialog.show();
            }));
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        public void doTime() {

        }
    }
}

