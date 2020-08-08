package pers.hu.oneradio.net;

import pers.hu.oneradio.utils.othertest.DjCategory;

final public class SmartUrlGetter {
    final static String site = "http://123.57.248.202:3000";

    public static String getSongDetailById(long id) {
        return site + "/song/detail?ids=" + id;
    }

    public static String getSongUrlDataById(long id) {
        return site + "/song/url?id=" + id;
    }

    public static String getHotDjRaw() {
        return site + "/dj/hot";
    }

    public static String getDjDetailByRid(long id) {
        return site + "/dj/detail?rid=" + id;
    }

    public static String getDjDetailWithProgramsByRid(long id,int offset) {
        return site + "/dj/program?rid=" + id + "&limit=20"+"&offset="+offset;
    }

    public static String getProgramDetailById(long id) {
        return site + "/dj/program/detail?id=" + id;
    }

    public static String getRecommendDjRaw() {
        return site + "/dj/recommend";
    }

    public static String getDjByCateId(@DjCategory int category) {
        return site + "/dj/radio/hot?cateId=" + category;
    }

    public static String getProgramTopList() {
        return site + "/dj/program/toplist";
    }
}
