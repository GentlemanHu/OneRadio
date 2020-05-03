package pers.hu.oneradio.utils.parser;

import android.util.Pair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializableSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.SingleObjectModel;
import pers.hu.oneradio.net.model.SongData;

public class SingleSongParser {
    private static SingleDetailDownloader downloader = new SingleDetailDownloader();
    private List<SongData> songDatas;
    private SongData songData;

    //暂且使用string传递，用流或者其他类型可能更安全
    public SingleObjectModel parseDetail(String jsonstr) {
        SingleObjectModel singleObjectModel = (SingleObjectModel) JSON.parseObject(jsonstr, SingleObjectModel.class);
        //一般来说单曲数组只有一个，获取第一个就行。但有可能nullpointer。
        songData = singleObjectModel.getData()[0];
        return singleObjectModel;
    }

    public List<SongData> parserSong(String str) {
        songDatas = new ArrayList<>();
        JSONObject obj = JSON.parseObject(str);
        JSONArray array = obj.getJSONArray("djRadios");
        Iterator it = array.iterator();
        //获取djRadio节目的detailID，并暂时存储
        while (it.hasNext()) {
            songData = new SongData();
            JSONObject object = (JSONObject) it.next();
            long detailID = Integer.valueOf((Integer) object.get("id")).longValue();
            System.out.println(detailID);
            String[] result = getRealURL(detailID);

            songData.setId(detailID);
//            songData.setPicUrl(result[1]);
//            songData.setUrl(result[0]);

            songDatas.add(songData);
        }
        //通过detailID获取真实songID，在通过songID，获取真实url

        return songDatas;
    }

    public SongData getSong() {
        return songData;
    }

    //TODO:优化缩减！写的比较繁琐冗余
    public String[] getRealURL(long detailID) {
        String url = "";
        String picUrl = "";


        //获取真实ID
        String raw = downloader.getData(SmartUrlGetter.getSongDetailById(detailID));
        JSONObject object1 = JSON.parseObject(raw);
        JSONArray array = object1.getJSONArray("songs");
        System.out.println(array);
        JSONObject object = (JSONObject) array.get(0);
        JSONObject object2 = (JSONObject) object.get("al");

        picUrl=(String) object2.get("picUrl");

        System.out.println(picUrl);
        //通过ID获取URL
        String raw2 = downloader.getSongDetailById(Integer.valueOf((int)object2.get("id")).longValue());

        JSONObject urlObj = JSON.parseObject(raw2);
        JSONArray urlArray = urlObj.getJSONArray("data");
        JSONObject urlPair = (JSONObject) urlArray.get(0);

        url = (String) urlPair.get("url");

        System.out.println(url);

        String[] result = new String[]{url,picUrl};
        return result;
    }
}
