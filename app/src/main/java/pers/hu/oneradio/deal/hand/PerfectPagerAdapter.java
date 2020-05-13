package pers.hu.oneradio.deal.hand;

import android.os.Handler;
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
    private PerfectPagerAdapter adapter;
    private int position;

    public void addDj(DjDetail dj) {
        djs.add(dj);
    }

    public ArrayList<DjDetail> getDjs() {
        return djs;
    }

    public void setDjs(ArrayList<DjDetail> djs) {
        this.djs = djs;
    }

    private ArrayList<DjDetail> djs = new ArrayList<>();
    private Handler handler;
    private ImageView homebg;
    private FragmentManager fm;
    private List idList;
    private ArrayList<CommonFragment> fragments = new ArrayList<CommonFragment>();
    private Random random = new Random();

    public PerfectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public interface DownloadDateCallback {
        void onComplete(PerfectPagerAdapter adapter);
    }

    public PerfectPagerAdapter(FragmentManager fm, Handler handler, Integer[] ids) {
        super(fm);
        adapter = this;
        this.ids = ids;
        this.handler = handler;
        this.fm = fm;
        idList = Arrays.stream(ids).collect(Collectors.toList());
        init();
    }

    public PerfectPagerAdapter(FragmentManager fm, Handler handler, Integer[] ids, ImageView homebg) {
        super(fm);
        this.homebg = homebg;
        adapter = this;
        this.ids = ids;
        this.handler = handler;
        this.fm = fm;
        init();
    }

    public PerfectPagerAdapter(FragmentManager fm, Integer[] ids) {
        super(fm);
        this.ids = ids;
        init();
    }

    @Override
    public Fragment getItem(int position) {
        this.position = position;
        //id过少自动请求添加
        if (idList.size() < 3) {
            GetDjIdTask task = new GetDjIdTask();
            task.execute(DjBoardEnum.RCD);
            try {
                ids = task.get();
                List list = Arrays.stream(ids).collect(Collectors.toList());
                synchronized (idList) {
                    idList.addAll(list);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (idList.size() != 0) {
            final DjSingleTask task = new DjSingleTask(fragments.get(position), this);
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    task.execute(idsSmartGetter(), "jjf");
                }
            };
            handler.postDelayed(r, 100);
        }
        return fragments.get(position);
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

    public void addItem() {
        //保证队列里不能过度添加，和后退时不添加
        if (fragments.size() - position < 7) {
            CommonFragment fragment = new CommonFragment();
            synchronized (fragments) {
                fragments.add(fragment);
                notifyDataSetChanged();
            }
        }
    }

    private String idsSmartGetter() {
        synchronized (idList) {
            String id = String.valueOf(idList.get(0));
            idList.remove(0);
            return id;
        }
    }

    private void init() {
        Runnable init = new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (index < 5) {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            addItem();
                        }
                    };
                    index++;
                    handler.postDelayed(r, 100);
                }
            }
        };
        init.run();
    }

}
