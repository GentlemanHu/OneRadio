package pers.hu.oneradio.utils.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializableSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;

import java.util.Iterator;

import pers.hu.oneradio.net.model.SingleObjectModel;
import pers.hu.oneradio.net.model.SongData;

public class SingleSongParser {
    private SongData songData;
    //暂且使用string传递，用流或者其他类型可能更安全
    public SingleObjectModel parseDetail(String jsonstr){
        SingleObjectModel singleObjectModel = (SingleObjectModel)JSON.parseObject(jsonstr,SingleObjectModel.class);
        //一般来说单曲数组只有一个，获取第一个就行。但有可能nullpointer。
        songData = singleObjectModel.getData()[0];
        return singleObjectModel;
    }

    public void parserSong(String str){

        JSONObject obj = JSON.parseObject(str);
        JSONArray array = obj.getJSONArray("djRadios");

        int size = array.size();


        System.out.println(array.toJSONString());
    }
    public SongData getSong(){
        return songData;
    }
}
