package pers.hu.oneradio.feel.home.animation;

import android.widget.ImageView;

public class BgColorControl extends Thread{
    ImageView view;
    public BgColorControl(){
    }

    public void autoBgColor(ImageView view){
        this.view = view;

    }
}
