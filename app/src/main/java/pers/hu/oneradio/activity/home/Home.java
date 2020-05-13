package pers.hu.oneradio.activity.home;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.provider.SongInfo;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.richpath.RichPath;
import com.richpath.RichPathView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.R;
import pers.hu.oneradio.activity.PerfectActivity;
import pers.hu.oneradio.deal.hand.async.GetDjIdTask;
import pers.hu.oneradio.deal.hand.handler.DjIdHandler;
import pers.hu.oneradio.deal.music.MainService;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.feel.home.animation.MoreAnimation;
import pers.hu.oneradio.feel.home.animation.Transition;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.feel.home.perfectview.CustPagerTransformer;
import pers.hu.oneradio.net.model.DjBoardEnum;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.deal.hand.async.DjSingleTask;
import pers.hu.oneradio.deal.hand.async.DjTask;
import pers.hu.oneradio.deal.hand.PerfectPagerAdapter;
import pers.hu.oneradio.deal.hand.handler.DetailHandler;
import pers.hu.oneradio.deal.hand.handler.PictureTaskHandler;
import pers.hu.oneradio.net.model.Program;
import pers.hu.oneradio.net.model.Song;

public class Home extends PerfectActivity {
    private ItemIconAnimation animation;
    private RichPathView load;
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
        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        adapter = new PerfectPagerAdapter(getSupportFragmentManager(), detailHandler, ids);

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

                Runnable add = new Runnable() {
                    @Override
                    public void run() {
                        adapter.addItem();
                    }
                };
                //预加载
                viewPager.setOffscreenPageLimit(adapter.getCount() - position);
                //稍微延迟请求，防止阻塞，减少卡顿感
                handler.postDelayed(add, 200);
                blurImage(position);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        getSongs(position);
                    }
                };
                handler.postDelayed(r, 1000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getSongs(int position) {
        dj = adapter.getDjs().get(position);
        songInfos = new ArrayList<>();
        Program[] songs = dj.getPrograms();

        for (Program s : songs) {
            Song song = s.getMainSong();

            SongInfo songInfo = new SongInfo();
            songInfo.setSongUrl(song.getUrl());
            songInfo.setSongId(s.getMainSong().getId().toString());
            System.out.println(song.getUrl());
            songInfos.add(songInfo);
        }

        djName.startAnimation(Transition.textSlide(this));
        if (dj.getRcmdtext() == null)
            djName.setText("同一个世界 同一个电台\n  --OneRadio");
        else if (dj.getRcmdtext() != null)
            djName.setText(dj.getRcmdtext());

        StarrySky.Companion.with().addPlayList(songInfos);
        StarrySky.Companion.with().playMusic(songInfos, 0);
    }

    private void blurImage(int position) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable d = adapter.getFragments().get(position).getImageView().getDrawable();
                    if (d != null) {
                        load.setVisibility(View.INVISIBLE);
                        Bitmap bitmap = drawableToBitmap(d);
                        homelayout.setBackground(d);
                        homebg.startAnimation(Transition.textSlide(Home.this));
                        homebg.setImageBitmap(bitmap);
                        Blurry.with(Home.this).animate(7000).sampling(2).from(bitmap).into(homebg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(r, 100);
    }

    public ArrayList<CommonFragment> getFragments() {
        return fragments;
    }

    public void boomInit() {
        boom.setButtonEnum(ButtonEnum.Ham);
        boom.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        boom.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);

        for (int i = 0; i < boom.getButtonPlaceEnum().buttonNumber(); i++) {
            boom.addBuilder(new HamButton.Builder()
                    .normalImageRes(R.drawable.command));
        }

        boom.setOnClickListener((v) -> {
            System.out.println("clicked!!!");
        });
    }

    //drawble转换bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //暂时适用的提示类
    class showMessageAsync extends AsyncTask<String, Void, String> {
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

            handler.postDelayed(init, 1000);
            return "ok";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(Home.this).create();
            alertDialog.setTitle("坐下，喝茶~");
            alertDialog.setIcon(R.drawable.command);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage("1.已知BUG：瞬间滑动多个，可能导致崩溃~\n2.发现新BUG请及时提出~\n3.第一个电台默认不自动播放，后面自动播放\n若想播放第一个，请先滑到后边，再划回来\n4.默认循环播放电台\n5.播放控制暂未添加（没法暂停）\n6.其他功能未开发，留给大佬发挥~请大佬们有空弄两下，哈哈~");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

    }
}

