package pers.hu.oneradio.deal.hand.async;

import android.content.Context;
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
    private Context context;
    private DjParser parser = new DjParser();
    private SingleDetailDownloader detailDownloader;


    public GetDjIdTask(Handler handler,Context context) {
        this.context = context;
        this.handler = handler;
        detailDownloader = new SingleDetailDownloader(context);
    }

    public GetDjIdTask(Context context) {
        this.context = context;
        detailDownloader = new SingleDetailDownloader(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        detailDownloader = new SingleDetailDownloader(context);
    }

    @Override
    protected void onPostExecute(Integer[] integers) {
        super.onPostExecute(integers);
        if (handler != null) {
            Message message = handler.obtainMessage(1, integers);
            System.out.println(integers+" all ids________------->>>>>>");
            message.sendToTarget();
        }
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Integer[] doInBackground(DjBoardEnum... enums) {
        System.out.println(detailDownloader.getUrlGetter().getConfig()+"get config from GetDjIdTask"+detailDownloader.getDjHot());
        switch (enums[0]) {
            case HOT:
                System.out.println("download from HOT--->>>>");
                String raw = detailDownloader.getDjHot();
                parser.parseDjs(raw);
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
                System.out.println("download from RCD----->>>");
                String raw2 = detailDownloader.getDjRecommend();
                parser.parseDjs(raw2);
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
                //TODO：为避免null，暂时使用HOT中代替TODAY，待到完善接口，需要更改
                System.out.println("not implemented yet!");
                String raw3=detailDownloader.getDjHot();
                parser.parseDjs(raw3);
                List<DjDetail> detailz = parser.getDjs();
                Integer[] idz = new Integer[detailz.size()];
                int indez = 0;
                for (DjDetail dj : detailz
                ) {
                    idz[indez] = dj.getId();
                    indez++;
                }
                publishProgress(true);
                return idz;
            default:
                return null;
        }
    }
}
