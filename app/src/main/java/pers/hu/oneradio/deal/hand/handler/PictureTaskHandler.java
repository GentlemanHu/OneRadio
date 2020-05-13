package pers.hu.oneradio.deal.hand.handler;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.activity.home.Home;

//detailhandler 包含了新功能
@Deprecated
public class PictureTaskHandler extends Handler {
   private Home home;
    private ImageView imageView;

    public PictureTaskHandler() {
    }

    public PictureTaskHandler(ImageView imageView) {
        this.imageView = imageView;
    }

    public PictureTaskHandler(ImageView imageView, Home home) {
        this.imageView = imageView;
        this.home = home;
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                Blurry.with(imageView.getContext()).from((Bitmap) msg.obj).into(imageView);
                System.out.println("ok???>>>");
                break;
            default:
                break;
        }
    }
}
