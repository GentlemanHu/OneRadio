package pers.hu.oneradio.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.R;
import pers.hu.oneradio.feel.home.animation.MoreAnimation;
import pers.hu.oneradio.feel.home.animation.Transition;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.feel.home.perfectview.CustPagerTransformer;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.tool.hand.DjTask;
import pers.hu.oneradio.tool.hand.PerfectPagerAdapter;

public class Home extends PerfectActivity {
    private MoreAnimation animation;
    private ImageView homebg;
    private PerfectPagerAdapter adapter;
    private int viewPagerPosition = 5;
    private ArrayList<CommonFragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private BoomMenuButton boom;
    private Random random = new Random();
    private Handler handler = new Handler();
    private String[] imageArray2 = new String[100];
    private String[] imageArray = new String[100];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        homebg = findViewById(R.id.homebg);

        boom = findViewById(R.id.boom);
        animation = new MoreAnimation(boom);

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

        init();
    }

    //初始化，采用handler异步延迟启动，防止阻塞主线程
    private void init() {
        DjTask djTask = new DjTask(this, new Integer[]{794627365, 791601386, 618058, 793071394, 404005, 350052547, 338996078, 793339521}, Home.this);
        djTask.execute();
        final Runnable back = () -> {
            while (true) {
                try {
                    if (djTask.get() instanceof DjDetail[]) {
                        fillViewPager();
                        break;
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            viewPager.setCurrentItem(viewPagerPosition, true);
            System.out.println(getIntent().getIntExtra("currentItem",1)+"intent with init");
        };
        handler.postDelayed(back, 300);
    }

    private void fillViewPager() {
        viewPager = findViewById(R.id.viewpager);

        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        imageArray2 = new String[]{"assets://image1.jpg","assets://image2.jpg","assets://image3.jpg","assets://image4.jpg","assets://image5.jpg"};

        for (int i = 0; i < 10; i++) {
            CommonFragment fragment = new CommonFragment();
            fragment.bindData(imageArray2[random.nextInt(imageArray2.length)]);
            fragments.add(fragment);
        }
        adapter = new PerfectPagerAdapter(getSupportFragmentManager(),imageArray2,fragments);

        viewPager.setAdapter(adapter);


        // 3. viewPager滑动时，调整指示器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                CommonFragment fragment = new CommonFragment();
                fragment.bindData(imageArray2[random.nextInt(imageArray2.length)]);
                fragments.add(fragment);
                //adapter.setFragments(fragments);
            }

            @Override
            public void onPageSelected(int position) {
                ImageLoader.getInstance().displayImage(fragments.get(position).getUrl(),homebg, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        Blurry.with(Home.this).animate().from(loadedImage).into(homebg);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
                System.out.println(position);
                viewPagerPosition = position;
                System.err.println(viewPagerPosition + ",,," + viewPager.getVerticalScrollbarPosition());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        SharedPreferences sp = getSharedPreferences("currentItemPosition", 0);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("currentItem",viewPagerPosition);
//        editor.commit();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences sp = getSharedPreferences("currentItemPosition", 0);
//        viewPagerPosition=sp.getInt("currentItem",5);
//    }

    public void setImageUrl(int index, String url) {
        imageArray[index] = url;
    }
}
