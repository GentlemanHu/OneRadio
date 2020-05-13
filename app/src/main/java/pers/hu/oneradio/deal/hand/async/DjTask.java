package pers.hu.oneradio.deal.hand.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONPath;

import java.util.ArrayList;

import pers.hu.oneradio.activity.home.Home;
import pers.hu.oneradio.activity.start.Forward;
import pers.hu.oneradio.feel.home.animation.Transition;
import pers.hu.oneradio.feel.home.animation.ItemIconAnimation;
import pers.hu.oneradio.net.downloader.SingleDetailDownloader;
import pers.hu.oneradio.net.model.DjDetail;

public class DjTask extends AsyncTask<Void, Boolean, DjDetail[]> {
    private SingleDetailDownloader downloader = new SingleDetailDownloader();
    private Context context;
    private Forward activity;
    private String[] strings;
    private Integer id;
    private Integer[] ids;
    private TextView text;
    private ArrayList<Object> whatobj = new ArrayList<>();
    private ItemIconAnimation[] animations;

    public DjTask() {
    }

    public DjTask(Forward activity, Integer id, TextView text, ItemIconAnimation... animations) {
        this.activity = activity;
        whatobj.add(text);
        this.id = id;
        this.text = text;
        this.animations = new ItemIconAnimation[animations.length];
        for (int i = 0; i < animations.length; i++) {
            this.animations[i] = animations[i];
        }
    }

    public DjTask(Context context, Integer[] ids, ItemIconAnimation... animations) {
        this.context = context;
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
        if (djDetails != null&&whatobj.size()!=0) {
            super.onPostExecute(djDetails);
            if (whatobj.get(0) instanceof TextView) {
                for (DjDetail djDetail : djDetails
                ) {
                    if (djDetail.getRcmdtext() == null) {
                        text.startAnimation(Transition.textSlide(text.getContext()));
                        text.setText("同一个世界，同一个电台\n     --OneRadio");
                    }
                    if (djDetail.getRcmdtext() != null) {
                        text.startAnimation(Transition.textSlide(text.getContext()));
                        text.setText(djDetail.getRcmdtext());
                    }
                }
            } else if (whatobj.get(0) instanceof Home) {

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
                    //animation.cancelAnimOne();
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
                    result[index] = dj;
                    index++;
                }
                publishProgress(true);
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
