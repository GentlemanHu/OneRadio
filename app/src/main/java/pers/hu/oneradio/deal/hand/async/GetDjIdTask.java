package pers.hu.oneradio.deal.hand.async;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjBoardEnum;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.utils.parser.DjParser;

public class GetDjIdTask extends AsyncTask<DjBoardEnum, Boolean, Integer[]> {
    private Handler handler;
    private DjParser parser = new DjParser();
    private SingleDetailDownloader detailDownloader = new SingleDetailDownloader();


    public GetDjIdTask() {
    }

    public GetDjIdTask(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer[] integers) {
        super.onPostExecute(integers);
        if (handler != null) {
            Message message = handler.obtainMessage(1, integers);
            message.sendToTarget();
        }
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Integer[] doInBackground(DjBoardEnum... enums) {
        switch (enums[0]) {
            case HOT:
                parser.parseDjs(detailDownloader.getDjHot());
                List<DjDetail> details = parser.getDjs();
                Integer[] ids = new Integer[details.size()];
                int index = 0;
                for (DjDetail dj : details
                ) {
                    ids[index] = dj.getId();
                    index++;
                }
                publishProgress(true);
                return ids;
            case RCD:
                parser.parseDjs(detailDownloader.getDjRecommend());
                List<DjDetail> djs = parser.getDjs();
                Integer[] idss = new Integer[djs.size()];
                int in = 0;
                for (DjDetail dj : djs
                ) {
                    idss[in] = dj.getId();
                    in++;
                }
                publishProgress(true);
                return idss;
            case TODAY:
                System.out.println("not implemented yet!");
                publishProgress(true);
                break;
            default:
                return null;
        }
        return null;
    }
}
