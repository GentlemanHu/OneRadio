package pers.hu.oneradio.deal.hand.async;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lzx.starrysky.provider.SongInfo;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.activity.home.Home;
import pers.hu.oneradio.deal.hand.PerfectPagerAdapter;
import pers.hu.oneradio.deal.listener.OnDataLoadCompleted;
import pers.hu.oneradio.deal.music.SongListManager;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.net.model.Program;
import pers.hu.oneradio.net.model.Song;
import pers.hu.oneradio.utils.parser.SingleSongParser;


public class DjSingleTask extends AsyncTask<String, Boolean, DjDetail> {
    private Handler handler;
    private CommonFragment fragment;
    private Bitmap image;
    private SingleDetailDownloader downloader = new SingleDetailDownloader();
    private SingleSongParser parser = new SingleSongParser();

    @Deprecated
    @SuppressWarnings("由于暂且在Home中使用listen方式填充背景，handler暂时没用")
    public DjSingleTask(Handler handler) {
        this.handler = handler;
    }

    public DjSingleTask() {

    }

    public DjSingleTask(CommonFragment fragment, Handler handler) {
        this.fragment = fragment;
        this.handler = handler;
    }

    @Override
    protected DjDetail doInBackground(String... strings) {
        if (strings.length == 0)
            return null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //通过rid获取dj详情
        DjDetail dj = new DjDetail();
        String raw = downloader.getDjDetailByRid(Integer.valueOf(strings[0]));
        dj.setId((Integer) JSONPath.eval(raw, "$.djRadio.id"));
        dj.setPicUrl((String) JSONPath.eval(raw, "$.djRadio.picUrl"));
        dj.setRcmdtext((String) JSONPath.eval(raw, "$.djRadio.rcmdText"));
        dj.setCategory((String) JSONPath.eval(raw, "$.djRadio.category"));
        dj.setName((String) JSONPath.eval(raw, "$.djRadio.name"));


        try {
            URL url = new URL(dj.getPicUrl());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (strings.length > 1) {
            //通过dj的rid获取program的详情
            int offset = 1;
            String program_raw = null;
            while (offset < 2) {
                program_raw = downloader.getDjDetailWithProgramsByRid(dj.getId().longValue(), offset);
                if ((Integer) JSONPath.eval(program_raw, "$.count") == 0)
                    break;
                else {
                    ArrayList<JSONObject> programs = (ArrayList<JSONObject>) JSONPath.eval(program_raw, "$.programs[0:]");
                    Program[] pro = new Program[programs.size()];
                    int index = 0;
                    //获取program下mainsong，设置数据
                    for (JSONObject o : programs) {
                        Program program = o.toJavaObject(Program.class);
                        pro[index] = program;
                        //给歌设置真实可播放连接
                        String song_url = parser.parseSongUrl(program.getMainSong().getId());
                        program.getMainSong().setUrl(song_url);
                        index++;
                    }
                    dj.setPrograms(pro);
                    offset++;
                    program_raw = downloader.getDjDetailWithProgramsByRid(dj.getId().longValue(), offset);
                }
            }
        }
        publishProgress(true);
        return dj;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(DjDetail djDetail) {
        super.onPostExecute(djDetail);
        if (fragment != null && handler != null) {
            try {
                fragment.updateImage(image);
                addSongListAndSet(djDetail,fragment.getPosition());
            } catch (Exception e) {
                System.out.println(e);
            }
            handler.sendEmptyMessage(1);
        }
        //TODO:加载信息
        if (handler != null) {
            Message message = handler.obtainMessage(1, djDetail);
            message.sendToTarget();
        }
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
    }

    private void addSongListAndSet(DjDetail dj,int position){
        ArrayList songInfos = new ArrayList();
        Program[] songs = dj.getPrograms();
        for (Program s : songs) {
            Song song = s.getMainSong();
            SongInfo songInfo = new SongInfo();
            songInfo.setSongUrl(song.getUrl());
            songInfo.setSongId(s.getMainSong().getId().toString());
            songInfo.setSongName(dj.getName());
            songInfo.setArtist(dj.getRcmdtext());
            songInfo.setSongCover(dj.getPicUrl());
            System.out.println(song.getUrl());
            songInfos.add(songInfo);
        }

        SongListManager.addPlayList(position, songInfos);
        SongListManager.addDjDetail(dj);
    }




}
