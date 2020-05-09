package pers.hu.oneradio.tool.hand;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONPath;

import java.util.ArrayList;

import pers.hu.oneradio.activity.Home;
import pers.hu.oneradio.activity.MainActivity;
import pers.hu.oneradio.activity.PerfectActivity;
import pers.hu.oneradio.feel.home.animation.Transition;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;

public class DjTask extends AsyncTask<Void, Boolean, DjDetail[]> {
    private Home home;
    private SingleDetailDownloader downloader = new SingleDetailDownloader();
    private Context context;
    private MainActivity activity;
    private String[] strings;
    private Integer id;
    private Integer[] ids;
    private TextView text;
    private ArrayList<Object> whatobj = new ArrayList<>();
    private ItemIconAnimation[] animations;

    public DjTask() {
    }

    public DjTask(MainActivity activity, Integer id, TextView text, ItemIconAnimation... animations) {
        this.activity = activity;
        whatobj.add(text);
        this.id = id;
        this.text = text;
        this.animations = new ItemIconAnimation[animations.length];
        for (int i = 0; i < animations.length; i++) {
            this.animations[i] = animations[i];
        }
    }

    public DjTask(Context context, Integer[] ids, Home home, ItemIconAnimation... animations) {
        this.context = context;
        whatobj.add(home);
        this.home = home;
        this.ids = ids;
        if (animations != null) {
            this.animations = new ItemIconAnimation[animations.length];
            for (int i = 0; i < animations.length; i++) {
                this.animations[i] = animations[i];
            }
        }
    }

    @Override
    protected void onPreExecute() {
        if (animations != null) {
            for (ItemIconAnimation animation : animations
            ) {
                if (animation.isIsBigAnim()) {
                    animation.cancelBigAnim();
                }
                if (!animation.isIsOneAnim())
                    animation.animateCommand();
            }
        }
    }

    @Override
    protected void onPostExecute(DjDetail[] djDetails) {
        if (djDetails != null) {
            super.onPostExecute(djDetails);
            if (whatobj.get(0) instanceof TextView) {
                for (DjDetail djDetail : djDetails
                ) {

                    if (djDetail.getRcmdtext() == null) {
                        text.startAnimation(Transition.textSlide(text.getContext()));
                        text.setText("这是一个好电台~");
                    }
                    if (djDetail.getRcmdtext() != null) {
                        text.startAnimation(Transition.textSlide(text.getContext()));
                        text.setText(djDetail.getRcmdtext());
                    }
                }
            } else if (whatobj.get(0) instanceof Home) {
                int index = 0;
                for (DjDetail djDetail : djDetails
                ) {
                    System.err.println(djDetail.getPicUrl());
                    home.setImageUrl(index, djDetail.getPicUrl());
                    index++;
                }
            }
        }
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
        if (animations != null) {
            for (ItemIconAnimation animation : animations
            ) {
                if (animation.isIsOneAnim())
                    animation.cancelAnimOne();
                if (!animation.isIsBigAnim())
                    animation.openBigAnimation();
            }
        }
        //加载结束
    }

    @Override
    protected DjDetail[] doInBackground(Void... voids) {
        try {
            //暂时没想到更好解决办法，就暂且判断是否是用了第二个构造方法，没输入多个id；
            if (ids == null) {
                DjDetail[] result = new DjDetail[1];
                String raw = downloader.getDjDetailByRid(id);
                DjDetail dj = new DjDetail();
                dj.setId((Integer) JSONPath.eval(raw, "$.djRadio.id"));
                dj.setPicUrl((String) JSONPath.eval(raw, "$.djRadio.picUrl"));
                dj.setRcmdtext((String) JSONPath.eval(raw, "$.djRadio.rcmdText"));
                System.out.println(dj.getId() + "," + dj.getRcmdtext());
                result[0] = dj;
                publishProgress(true);
                return result;
            } else
            //输入了多个ids情况
            {
                DjDetail[] result = new DjDetail[ids.length];
                int index = 0;
                for (Integer id : ids
                ) {
                    String raw = downloader.getDjDetailByRid(id);
                    DjDetail dj = new DjDetail();
                    dj.setId((Integer) JSONPath.eval(raw, "$.djRadio.id"));
                    dj.setPicUrl((String) JSONPath.eval(raw, "$.djRadio.picUrl"));
                    dj.setRcmdtext((String) JSONPath.eval(raw, "$.djRadio.rcmdText"));
                    System.out.println(dj.getId() + "," + dj.getRcmdtext());
                    result[index] = dj;
                    index++;
                }

                publishProgress(true);
                //System.out.println(raw);
                return result;
            }
        } catch (Exception e) {
            //必须在主UI线程Toast
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "网络超时或其他错误，请重试", Toast.LENGTH_LONG).show();
                    animations[0].cancelAnimOne();
                }
            });
            return null;
        }
    }

}
