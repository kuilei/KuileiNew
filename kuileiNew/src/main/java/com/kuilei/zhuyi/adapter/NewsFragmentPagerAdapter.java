package com.kuilei.zhuyi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.kuilei.zhuyi.utils.Logger;

import java.util.ArrayList;

/**
 * Created by lenovog on 2016/6/29.
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private final FragmentManager fm;

    public NewsFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void appendList(ArrayList<Fragment> fragment)
    {
        fragments.clear();
        if (!fragments.containsAll(fragment) && fragment.size() > 0)
        {
            fragments.addAll(fragment);
        }
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Logger.w(NewsFragmentPagerAdapter.class,"fragment = " + fragments.size() + "  position = " + position);
        if (fragments.size() <= position)
        {
            position = position % fragments.size();
        }
        Object obj = super.instantiateItem(container,position);
        return obj;
    }
}
