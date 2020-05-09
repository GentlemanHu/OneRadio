package pers.hu.oneradio.feel.home.perfectview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;

/**
 * 锁定宽高比的CardView
 * Created by xmuSistone on 2016/9/21.
 */
public class AspectRatioCardView extends CardView {

    private float ratio = 1.2f;

    public AspectRatioCardView(Context context) {
        this(context, null);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AspectRatioCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (ratio > 0) {
            int ratioHeight = (int) (getMeasuredWidth() * ratio);
            setMeasuredDimension(getMeasuredWidth(), ratioHeight);
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = ratioHeight;
            setLayoutParams(lp);
        }
    }
}