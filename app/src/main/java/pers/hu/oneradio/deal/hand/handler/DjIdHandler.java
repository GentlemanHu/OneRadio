package pers.hu.oneradio.deal.hand.handler;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import pers.hu.oneradio.net.model.DjDetail;

public class DjIdHandler extends Handler {
    private Integer[] ids;

    public DjIdHandler() {
    }

    //TODO：应该处理其他事物，暂时没想明白
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                ids = (Integer[]) msg.obj;
                break;
            default:
                break;
        }
    }

    public Integer[] getIds(){
        return ids;
    }
}
