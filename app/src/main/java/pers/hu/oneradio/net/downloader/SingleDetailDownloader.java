package pers.hu.oneradio.net.downloader;



import org.jsoup.Jsoup;

import java.net.URL;

public class SingleDetailDownloader extends BaseDetailDownloader {
    @Override
    public String getData(String url) {
        String content = "";
        //send request and get data
        try {
           content =  Jsoup.connect(url).ignoreContentType(true).execute().body();
           //System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
