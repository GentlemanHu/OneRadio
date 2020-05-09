package pers.hu.oneradio.net.downloader;



import org.jsoup.Jsoup;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.utils.othertest.DjCategory;

public class SingleDetailDownloader extends BaseDetailDownloader {
    //TODO:未使用异步处理

    @Override
    public String getData(String url) {
        String content = "";
        //send request and get data
        try {
           content =  Jsoup.connect(url).ignoreContentType(true).maxBodySize(0).timeout(20000).execute().body();
           //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public  String getDjHot(){
        return getData(SmartUrlGetter.getHotDjRaw());
    }

    public  String getDjRecommend(){
        return getData(SmartUrlGetter.getRecommendDjRaw());
    }

    public  String getDjByCateId(@DjCategory int category){
        return getData(SmartUrlGetter.getDjByCateId(category));
    }

    public String getDjProgramDetail(long id){
        return getData(SmartUrlGetter.getProgramDetailById(id));
    }

    public String getDjDetailWithProgramsByRid(long id){
        return getData(SmartUrlGetter.getDjDetailWithProgramsByRid(id));
    }

    public String getDjDetailByRid(long rid){
        return getData(SmartUrlGetter.getDjDetailByRid(rid));
    }

    public String getSongUrlDataById(long id){
        return getData(SmartUrlGetter.getSongUrlDataById(id));
    }

    public String getSongDetailById(long id){
        return getData(SmartUrlGetter.getSongDetailById(id));
    }
}
