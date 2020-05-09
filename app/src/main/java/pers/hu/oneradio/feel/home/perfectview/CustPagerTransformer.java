package pers.hu.oneradio.feel.home.perfectview;

import android.content.Context;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

/**
 * 实现ViewPager左右滑动时的时差
 * Created by xmuSistone on 2016/9/18.
 * Reused by Gentleman.Hu on 2020/05/08
 * from https://github.com/xmuSistone/ViewpagerTransition/blob/master/android-page-transition/app/src/main/java/com/stone/transition/CustPagerTransformer.java
 */
public class CustPagerTransformer implements ViewPager.PageTransformer {

    private int maxTranslateOffsetX;
    private ViewPager viewPager;

    public CustPagerTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 180);
    }

    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }

        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
    }

    /**
     * dp和像素转换
     */
    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

}