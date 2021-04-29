package pers.hu.oneradio.deal.hand;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import pers.hu.oneradio.activity.home.Home;
import pers.hu.oneradio.base.TaskExecutor;
import pers.hu.oneradio.deal.hand.async.GetDjIdTask;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.deal.hand.async.DjSingleTask;
import pers.hu.oneradio.net.model.DjBoardEnum;
import pers.hu.oneradio.net.model.DjDetail;

public class PerfectPagerAdapter extends SmartFragmentStatePagerAdapter implements Serializable {
    private Integer[] ids;
    private Context context;
    private int position = -1;
    private volatile int counter = 0;
    private Handler handler;
    private List idList;
    private volatile ArrayList<CommonFragment> fragments = new ArrayList<CommonFragment>();
    private Random random = new Random();

    public PerfectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PerfectPagerAdapter(FragmentManager fm, Integer[] ids, Context context) {
        super(fm);
        this.ids = ids;
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        notifyDataSetChanged();
                        ((Home) context).onTaskCompleted(position);
                        break;
                    default:
                        break;
                }
            }
        };
        idList = Arrays.stream(ids).collect(Collectors.toList());
        init();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addItem() {
        //保证队列里不能过度添加，和后退时不添加
//        if (fragments.size() < idList.size()) {
//            Log.println(Log.ERROR, "AddItem", "添加Item，fragment大小" + fragments.size());
        add2DjList();
//        }
    }

    private void init() {
        int i = 0;
        while (i < 10) {
            fragments.add(new CommonFragment());
            notifyDataSetChanged();
            add2DjList();
            i++;
        }
        notifyDataSetChanged();
    }


    private void add2DjList() {
        Runnable r = () -> {
            synchronized (idList) {
                addFragment();
                CommonFragment fragment = fragments.get(counter);
                // 设置postion
                fragment.setPosition(counter);
                final DjSingleTask task = new DjSingleTask(fragment, handler, context);
                String id = String.valueOf(idList.get(0));
                idList.remove(0);
                counter++;
                Log.println(Log.ERROR, "IDLIST Size", "歌单ID大小" + idList.size()+"<->>>counter"+counter+"<----fragment position"+fragment.getPosition());
                refreshIdList();
                task.execute(id, "useless");
            }
        };
        TaskExecutor.runInBackground(r);
    }

    private void refreshIdList() {
        //id过少自动请求添加
        synchronized (idList) {
            if (idList.size() < 3) {
                GetDjIdTask task = new GetDjIdTask(context);
                task.execute(random.nextInt(100) > 50 ? DjBoardEnum.HOT : DjBoardEnum.RCD);
                try {
                    ids = task.get();
                    List list = Arrays.stream(ids).collect(Collectors.toList());
                    idList.addAll(list);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addFragment() {
        TaskExecutor.runInMainThread(() -> {
            fragments.add(new CommonFragment());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public int getItemPosition(Object object) {
        return fragments.indexOf(object);
    }


    public ArrayList<CommonFragment> getFragments() {
        return fragments;
    }
}
