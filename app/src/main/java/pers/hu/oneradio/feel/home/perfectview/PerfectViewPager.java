package pers.hu.oneradio.feel.home.perfectview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import pers.hu.oneradio.activity.home.Home;

public class PerfectViewPager extends ViewPager {
    private PagerAdapter adapter;
    private Context context;

    public PerfectViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setDefaultTransformer(){
        setPageTransformer(false, new CustPagerTransformer(context));
    }
}
