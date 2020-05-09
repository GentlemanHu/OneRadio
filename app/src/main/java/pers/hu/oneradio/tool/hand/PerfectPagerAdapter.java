package pers.hu.oneradio.tool.hand;

import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Random;

import pers.hu.oneradio.feel.home.perfectview.CommonFragment;

public class PerfectPagerAdapter extends FragmentStatePagerAdapter {
    private String[] urls;
    private ArrayList<CommonFragment> fragments;
    private Random random = new Random();

    public PerfectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PerfectPagerAdapter(FragmentManager fm, String[] urls, ArrayList<CommonFragment> fragmentArrayList) {
        super(fm);
        this.fragments = fragmentArrayList;
        this.urls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        CommonFragment fragment = fragments.get(position);
        return fragment;
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public void setFragments(ArrayList<CommonFragment> fragments) {
        this.fragments = fragments;
    }
}
