package pers.hu.oneradio.net.downloader;


import android.content.Context;

import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.utils.othertest.DjCategory;

public class SingleDetailDownloader extends BaseDetailDownloader {
    private final OkHttpClient client = new OkHttpClient();
    //TODO:未使用异步处理
    private SmartUrlGetter urlGetter;

    public SingleDetailDownloader(Context context){
        urlGetter=new SmartUrlGetter(context);
        System.out.println(urlGetter.getConfig()+"<-----from SingleDetailDownloader");
    }
    @Override
    public String getData(String url) {
        String content = "";
        //send request and get data
        try {
            content = Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).timeout(20000).execute().body();
            //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //use okhttp test speed
        String ok_raw = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response responses = null;
            try {
                responses = client.newCall(request).execute();
                ok_raw = responses.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok_raw;
    }

    public String getDjHot() {
        System.out.println(urlGetter.getConfig()+"from getDjHOt");
        return getData(urlGetter.getHotDjRaw());
    }

    public String getDjRecommend() {
        return getData(urlGetter.getRecommendDjRaw());
    }

    public String getDjByCateId(@DjCategory int category) {
        return getData(urlGetter.getDjByCateId(category));
    }

    public String getDjProgramDetail(long id) {
        return getData(urlGetter.getProgramDetailById(id));
    }

    public String getDjDetailWithProgramsByRid(long id, int offset) {
        return getData(urlGetter.getDjDetailWithProgramsByRid(id, offset));
    }

    public String getDjDetailByRid(long rid) {
        return getData(urlGetter.getDjDetailByRid(rid));
    }

    public String getSongUrlDataById(long id) {
        return getData(urlGetter.getSongUrlDataById(id));
    }

    public String getSongDetailById(long id) {
        return getData(urlGetter.getSongDetailById(id));
    }

    public String getProgramTop() {
        return getData(urlGetter.getProgramTopList());
    }

    public SmartUrlGetter getUrlGetter(){
        return urlGetter;
    }
}
