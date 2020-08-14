package pers.hu.oneradio.deal.hand;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
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

import pers.hu.oneradio.deal.hand.async.GetDjIdTask;
import pers.hu.oneradio.feel.home.perfectview.CommonFragment;
import pers.hu.oneradio.deal.hand.async.DjSingleTask;
import pers.hu.oneradio.net.model.DjBoardEnum;
import pers.hu.oneradio.net.model.DjDetail;

public class PerfectPagerAdapter extends SmartFragmentStatePagerAdapter implements Serializable {
    private Integer[] ids;
    private Context context;
    private boolean doNotifyDataSetChangedOnce = true;
    private ArrayList<DjDetail> djs = new ArrayList<>();
    private Handler handler;
    private int index = 0;
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
        //id过少自动请求添加
        if (idList.size() - index < 3) {
            GetDjIdTask task = new GetDjIdTask(context);
            task.execute(DjBoardEnum.HOT);
            try {
                ids = task.get();
                List list = Arrays.stream(ids).collect(Collectors.toList());
                synchronized (idList) {
                    idList.addAll(list);
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (idList.size() != 0) {
            CommonFragment fragment = fragments.get(position);
            fragment.setPosition(position);
            final DjSingleTask task = new DjSingleTask(fragment, handler, context);
            synchronized (idList) {
                String id = String.valueOf(idList.get(index));
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        //TODO:获取电台
                        task.execute(id, "jjf");
                    }
                };
                handler.postDelayed(r, 100);
                index++;
            }
        }
        return fragments.get(position);
    }

    public void addItem() {
        //保证队列里不能过度添加，和后退时不添加
        if (fragments.size() < idList.size()) {
            CommonFragment fragment = new CommonFragment();
            synchronized (fragments) {
                fragments.add(fragment);
                notifyDataSetChanged();
            }
        }
    }

    private void init() {
        int index = 0;
        while (index < 5) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    CommonFragment fragment = new CommonFragment();
                    synchronized (fragments) {
                        fragments.add(fragment);
                    }
                    handler.sendEmptyMessage(1);
                }
            };
            r.run();
            notifyDataSetChanged();
            index++;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
        }
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
