package pers.hu.oneradio.feel.home.animation;

import android.app.Activity;
import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;


import pers.hu.oneradio.R;

public class Transition {
    static final Animation in = new AlphaAnimation(0.0f, 3.0f);
    static final Animation out = new AlphaAnimation(3.0f, 0.0f);
    static final AnimationSet as = new AnimationSet(true);


    public static void bounce(Activity activity){
        activity.overridePendingTransition(R.anim.bounce, android.R.anim.slide_out_right);
    }

    public static void blink(Activity activity){
        activity.overridePendingTransition(R.anim.blink,R.anim.blink);
    }

    public static void fade(Activity activity){
        activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public static void slide(Activity activity){
        activity.overridePendingTransition(R.anim.slidedown,R.anim.slideup);
    }

    public static void accelerator(Activity activity){
        activity.overridePendingTransition(R.anim.slidedown,R.anim.accelerator);
    }

    public static void test(Activity activity){
        activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    public static AnimationSet textFade(){
        as.addAnimation(out);
        in.setStartOffset(3000);
        as.addAnimation(in);
        return as;
    }

    public static AnimationSet imageFade(){
        as.addAnimation(out);
        in.setStartOffset(1000);
        as.addAnimation(in);
        return as;
    }
    public static Animation textSlide(Context context){
        return AnimationUtils.loadAnimation(context,R.anim.fadeout);
    }


}
