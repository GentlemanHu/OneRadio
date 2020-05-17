package pers.hu.oneradio.activity.home;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.exoplayer2.source.UnrecognizedInputFormatException;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.richpath.RichPathView;


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
    private OnTaskCompleted dealer = this;
    private RichPathView load;
    private static int position = 0;
    private Integer list_position = 0;
    private TextView djName;
    private ImageView homebg;
    private Random random = new Random();
    private RelativeLayout homelayout;
    private Context home;
    private DjDetail dj;
    private ArrayList<SongInfo> songInfos;
    private PerfectPagerAdapter adapter;
    private volatile ArrayList<CommonFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private Integer[] ids;
    private BoomMenuButton boom;
    private Handler handler = new Handler();
    private Handler detailHandler, pictureHandler, djIdHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        home = this.getApplicationContext();
        setContentView(R.layout.home);

        homebg = findViewById(R.id.homebg);
        djName = findViewById(R.id.djname);
        boom = findViewById(R.id.boom);
        load = findViewById(R.id.loading);
        homelayout = findViewById(R.id.homelayout);
        viewPager = findViewById(R.id.viewpager);

        animation = new ItemIconAnimation(load);
        pictureHandler = new PictureTaskHandler(homebg);
        detailHandler = new DetailHandler();
        djIdHandler = new DjIdHandler();

        animation.animateCommand();
        boomInit();

        new showMessageAsync().execute();

    }

    //初始化，采用handler异步延迟启动，防止阻塞主线程
    private void init() {
        final Runnable back = () -> {
            try {
                fillViewPager();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        handler.postDelayed(back, 300);
    }

    private void fillViewPager() {
        load.setVisibility(View.INVISIBLE);
        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        adapter = new PerfectPagerAdapter(getSupportFragmentManager(), detailHandler, ids, this);

        viewPager.setAdapter(adapter);
        //预加载
        viewPager.setOffscreenPageLimit(5);
        //动态添加page
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                load.setVisibility(View.VISIBLE);
                //预加载
                viewPager.setOffscreenPageLimit(adapter.getCount() - position);
                //稍微延迟请求，防止阻塞，减少卡顿感
                Home.position = position;
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
                if (SongListManager.getDjDetails().size() - 1 >= position) {
                    blurImage(position);
                    getSongs(position);
                } else {
                    new WaitSongReadyTask(dealer, position).execute();
                }

                last_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getSongs(int position) {
        Home.position = position;

        dj = SongListManager.getDjDetails().get(position);

        djName.startAnimation(Transition.textSlide(this));
        if (dj.getRcmdtext() == null)
            djName.setText("同一个世界 同一个电台\n  --OneRadio");
        else if (dj.getRcmdtext() != null)
            djName.setText(dj.getRcmdtext());
        songInfos = SongListManager.getSongPlaylist(position);

        StarrySky.Companion.with().addPlayList(songInfos);
        try {
            StarrySky.Companion.with().playMusic(songInfos, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "错误信息！！！");
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 50);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void boomInit() {
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

    @Override
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
                    ids = Arrays.stream(getIntent().getIntArrayExtra("ids")).boxed().toArray(Integer[]::new);
                    init();
                }
            };

            handler.postDelayed(init, 200);
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
            super.onPostExecute(result);
        }

        @Override
        public void doTime() {

        }
    }
}

