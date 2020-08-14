package pers.hu.oneradio;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySkyConfig;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.notification.CustomNotification;
import com.lzx.starrysky.notification.NotificationConfig;
import com.lzx.starrysky.notification.StarrySkyNotificationManager;
import com.lzx.starrysky.notification.utils.NotificationUtils;
import com.lzx.starrysky.provider.SongInfo;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.L;

import java.io.IOException;
import java.io.InputStream;

public class OneRadioApplication extends Application {
    private String config;

    @Override
    public void onCreate() {
        super.onCreate();
        initPlayer();
        initImageLoader();
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    private void initPlayer() {
        NotificationConfig notificationConfig = new NotificationConfig();
        notificationConfig.setTargetClass("pers.hu.oneradio.activity.home.Home");

        StarrySkyConfig config = new StarrySkyConfig().newBuilder()
                .isOpenNotification(true)
                .setNotificationFactory(StarrySkyNotificationManager.Companion.getSYSTEM_NOTIFICATION_FACTORY())
                .setNotificationConfig(notificationConfig)
                .build();
        StarrySky.Companion.init(this, config);
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(9)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSize(4 * 1024 * 1024).memoryCacheSizePercentage(18) // default
                .discCacheSize(580 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(fadeOption()) // default   DisplayImageOptions.createSimple()
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        //不显示高版本安卓no such field这类错误提醒
        L.writeLogs(false);
        imageLoader.init(config);
    }

    private DisplayImageOptions fadeOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new FadeInBitmapDisplayer(2500)).build();
        return options;
    }

}
