package pers.hu.oneradio.deal.listener;

import pers.hu.oneradio.net.model.DjDetail;

public interface OnTaskCompleted {
    void onTaskCompleted(int position);
    int getCurrentPosition();
}
