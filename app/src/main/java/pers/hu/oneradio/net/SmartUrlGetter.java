package pers.hu.oneradio.net;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import pers.hu.oneradio.OneRadioApplication;
import pers.hu.oneradio.utils.ConfigChecker;
import pers.hu.oneradio.utils.othertest.DjCategory;

// just extends Service to get context
public class SmartUrlGetter {

    private Context context;
    private String site;

    public static class UrlGetterProvider {
        private static final SmartUrlGetter instance = new SmartUrlGetter(OneRadioApplication.getContext());

        public static SmartUrlGetter getInstance() {
            return instance;
        }
    }

    private SmartUrlGetter(Context context) {
        this.context = context;
        this.site = ((OneRadioApplication) context.getApplicationContext()).getConfig();
        System.out.println("site:--->>" + site);
    }

    public String getSongDetailById(long id) {
        return site + "/song/detail?ids=" + id;
    }

    public String getSongUrlDataById(long id) {
        return site + "/song/url?id=" + id;
    }

    public String getHotDjRaw() {
        return site + "/dj/hot";
    }

    public String getDjDetailByRid(long id) {
        return site + "/dj/detail?rid=" + id;
    }

    public String getDjDetailWithProgramsByRid(long id, int offset) {
        return site + "/dj/program?rid=" + id + "&limit=20" + "&offset=" + offset;
    }

    public String getProgramDetailById(long id) {
        return site + "/dj/program/detail?id=" + id;
    }

    public String getRecommendDjRaw() {
        return site + "/dj/recommend";
    }

    public String getDjByCateId(@DjCategory int category) {
        return site + "/dj/radio/hot?cateId=" + category;
    }

    public String getProgramTopList() {
        return site + "/dj/program/toplist";
    }

    public String getConfig() {
        return site;
    }

}
