package pers.hu.oneradio.utils.parser;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pers.hu.oneradio.net.SmartUrlGetter;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.net.model.SingleObjectModel;
import pers.hu.oneradio.net.model.Song;

public class SingleSongParser {
    private  SingleDetailDownloader downloader;
    private List<Song> songs;
    private Context context;
    private Song song;
    private SmartUrlGetter urlGetter;

    public SingleSongParser(Context context){
        this.context = context;
        downloader = new SingleDetailDownloader(context);
        urlGetter=new SmartUrlGetter(context);
        System.out.println(urlGetter.getConfig()+"<-----from SingleSongParser");
    }
    public List<Song> toSongData(String programStr) {
        JSONObject object = JSON.parseObject(programStr);
        List<String> name = (List<String>) JSONPath.eval(object, "$programs[0:].mainSong.name");
        List<Integer> ids = (List<Integer>) JSONPath.eval(object, "$programs[0:].mainSong.id");
        songs = new ArrayList<Song>();
        Log.println(Log.ERROR,"Null",songs+","+ids.get(0));
        Iterator it = ids.iterator();
        while (it.hasNext()) {
            Integer id = (Integer) it.next();
            song = new Song();
            song.setId(id);
            song.setUrl(parseSongUrl(id));
            songs.add(song);
        }
        return songs;
    }

    public String parseSongUrl(long id) {
        String str = downloader.getSongUrlDataById(id);
        JSONObject object = JSON.parseObject(str);
        return (String) JSONPath.eval(object, "$data[0].url");
    }

    public List getSongData(String str) {
        List<DjDetail> djDetails = new ArrayList<DjDetail>();
        JSONObject obj = JSON.parseObject(str);
        JSONArray array = obj.getJSONArray("djRadios");
        for (Object o : array
        ) {
            JSONObject oj = (JSONObject) o;
            DjDetail dj = JSON.parseObject(oj.toJSONString(), DjDetail.class);
            djDetails.add(dj);
        }
        List<Integer> list = (List<Integer>) JSONPath.eval(obj, "$..id");
        for (Integer in : list
        ) {
            System.out.println(in);
        }
        return djDetails;
    }


    @Deprecated
    //暂且使用string传递，用流或者其他类型可能更安全
    public SingleObjectModel parseDetail(String jsonstr) {
        SingleObjectModel singleObjectModel = (SingleObjectModel) JSON.parseObject(jsonstr, SingleObjectModel.class);
        //一般来说单曲数组只有一个，获取第一个就行。但有可能nullpointer。
        song = singleObjectModel.getData()[0];
        return singleObjectModel;
    }

    @Deprecated
    public List<Song> parserSong(String str) {
        songs = new ArrayList<>();
        JSONObject obj = JSON.parseObject(str);
        JSONArray array = obj.getJSONArray("djRadios");
        Iterator it = array.iterator();
        //获取djRadio节目的detailID，并暂时存储
        while (it.hasNext()) {
            song = new Song();
            JSONObject object = (JSONObject) it.next();
            Integer detailID = (Integer) object.get("id");
            System.out.println(detailID);
            String[] result = getRealURL(detailID);
            song.setId(detailID);
            songs.add(song);
        }
        return songs;
    }

    public Song getSong() {
        return song;
    }

    @Deprecated
    //TODO:优化缩减！写的比较繁琐冗余
    public String[] getRealURL(long detailID) {
        String url = "";
        String picUrl = "";

        //获取真实ID
        String raw = downloader.getData(urlGetter.getDjDetailByRid(detailID));
        System.out.println(raw);
        JSONObject object1 = JSON.parseObject(raw);
        JSONObject djradio = (JSONObject) object1.get("djRadio");
        JSONObject lastProgramId = (JSONObject) djradio.get("lastProgramId");

        String[] result = new String[]{url, picUrl};
        return result;
    }
}
