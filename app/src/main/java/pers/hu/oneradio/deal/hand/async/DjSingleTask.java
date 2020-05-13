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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;
import pers.hu.oneradio.activity.home.Home;
import pers.hu.oneradio.deal.hand.PerfectPagerAdapter;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;
import pers.hu.oneradio.net.model.Program;
import pers.hu.oneradio.utils.parser.SingleSongParser;


public class DjSingleTask extends AsyncTask<String, Boolean, DjDetail> implements PerfectPagerAdapter.DownloadDateCallback {
    private Handler handler;
    private CommonFragment fragment;
    private FragmentManager fm;
    private PerfectPagerAdapter adapter;
    private SingleDetailDownloader downloader = new SingleDetailDownloader();
    private SingleSongParser parser = new SingleSongParser();

    @Deprecated
    @SuppressWarnings("由于暂且在Home中使用listen方式填充背景，handler暂时没用")
    public DjSingleTask(Handler handler) {
        this.handler = handler;
    }

    public DjSingleTask() {

    }

    public DjSingleTask(CommonFragment fragment, PerfectPagerAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }

    public DjSingleTask(CommonFragment fragment, PerfectPagerAdapter adapter, FragmentManager fm) {
        this.fragment = fragment;
        this.adapter = adapter;
        this.fm = fm;
    }

    public DjSingleTask(PerfectPagerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected DjDetail doInBackground(String... strings) {
        if (strings.length == 0)
            return null;
        //通过rid获取dj详情
        DjDetail dj = new DjDetail();
        String raw = downloader.getDjDetailByRid(Integer.valueOf(strings[0]));
        dj.setId((Integer) JSONPath.eval(raw, "$.djRadio.id"));
        dj.setPicUrl((String) JSONPath.eval(raw, "$.djRadio.picUrl"));
        dj.setRcmdtext((String) JSONPath.eval(raw, "$.djRadio.rcmdText"));

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
                    int index =0;
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
        if (fragment != null && adapter != null) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL(djDetail.getPicUrl());
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                fragment.updateImage(image);
                adapter.addDj(djDetail);
            } catch (IOException e) {
                System.out.println(e);
            }

            adapter.notifyDataSetChanged();
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

    //下载完毕后发送刷新UI信息
    @Override
    public void onComplete(PerfectPagerAdapter adapter) {
    }
}
