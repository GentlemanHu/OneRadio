package pers.hu.oneradio.deal.hand.handler;


import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.net.model.DjDetail;

public class DetailHandler extends Handler {
    private ImageView homebg;

    public DetailHandler(ImageView homebg) {
        this.homebg = homebg;
    }

    public DetailHandler() {
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                DjDetail detail = (DjDetail) msg.obj;
                break;
            default:
                break;
        }
    }
}
