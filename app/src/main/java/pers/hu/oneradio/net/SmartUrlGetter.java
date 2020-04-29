package pers.hu.oneradio.net;

public class SmartUrlGetter {
     final static String site = "http://www.crushing.xyz:3000";

     public static String getSongDetailById(long id){
          return site+"/song/detail?id="+id;
     }

     public static String getSongUrlDataById(long id){
          return site+"/song/url?id="+id;
     }

     public static String getHotDjRaw(){
          return site+"dj/hot";
     }

     public static String getRecommandDjRaw(){
          return site+"/personalized/djprogram";
     }
}
