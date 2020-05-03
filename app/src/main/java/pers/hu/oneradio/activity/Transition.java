package pers.hu.oneradio.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import pers.hu.oneradio.R;

public class Transition {
    public static void runFadeInAnimation(View view) {
        Animation a = AnimationUtils.loadAnimation(view.getContext(), R.anim.fadein);
        a.reset();
        LinearLayout ll = (LinearLayout) view;
        ll.clearAnimation();
        ll.startAnimation(a);
    }
}
