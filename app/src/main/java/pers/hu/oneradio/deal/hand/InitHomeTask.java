package pers.hu.oneradio.deal.hand;

import android.content.Context;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.L;
import java.util.ArrayList;
import java.util.List;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.feel.home.perfectview.CustPagerTransformer;



//由于不让在主线程外渲染，所以不用。暂且保留此类，作为参考学习
//已经使用Handler替代
@Deprecated
public class InitHomeTask extends AsyncTask<Void,Boolean,Boolean> {
    private Context context;
    private ViewPager pager;
    private FragmentActivity activity;
    private int viewPagerPosition = 1;
    private List<Fragment> fragments = new ArrayList<>();
    private ViewPager viewPager;
    private final String[] imageArray = {"assets://image1.jpeg", "assets://image2.jpeg", "assets://image3.jpeg", "assets://image4.jpeg", "assets://image5.jpeg"};

    public InitHomeTask(){}

    public InitHomeTask(Context context, FragmentActivity activity,ViewPager viewPager){
        this.context = context;
        this.activity = activity;
        this.viewPager = viewPager;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        System.out.println("加载结束！！");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("开始启动");
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        initImageLoader();
        fillViewPager();
        return true;
    }
    private void fillViewPager() {
        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(context));

        // 2. viewPager添加adapter
        for (int i = 0; i < 10; i++) {
            // 预先准备10个fragment
        }

        viewPager.setAdapter(new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = (CommonFragment)fragments.get(position % 10);
                fragment.bindData(imageArray[position % imageArray.length]);
                return fragment;
            }

            @Override
            public int getCount() {
                return 666;
            }
        });


        // 3. viewPager滑动时，调整指示器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                System.out.println(position);
                viewPagerPosition = position;
                System.err.println(viewPagerPosition+",,,"+viewPager.getVerticalScrollbarPosition());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        L.writeLogs(false);
        imageLoader.init(config);
    }

}
