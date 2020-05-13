package pers.hu.oneradio.deal.listener;

import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

public class PagerListener extends ViewPager.SimpleOnPageChangeListener {
    private ImageView bghome;
    public PagerListener() {
        super();
    }
    public PagerListener(ImageView bg){
        super();
        this.bghome = bg;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        System.out.println(position+"::selected");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }
}
