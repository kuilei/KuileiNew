package com.kuilei.zhuyi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.view.NewImageView;
import com.kuilei.zhuyi.view.NewImageView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/7/14.
 */
@EBean
public class ImageAdapter extends BaseAdapter {
    private final Class TAG = ImageAdapter.class;
    public List<String> lists = new ArrayList<String>();

    @RootContext
    Context context;

    public void appendList(List<String> list) {
        Logger.w(TAG,"appendList="+list.size());
            if (!lists.containsAll(list) && list != null && list.size() > 0) {
                lists.clear();
                lists.addAll(list);
            }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewImageView newImageView = null;
        if (convertView == null) {
            newImageView = NewImageView_.build(context);
        } else {
            newImageView = (NewImageView) convertView;
        }
        newImageView.setImage(lists, position);

        return newImageView;
    }

    public void clear() {
        lists.clear();
        notifyDataSetChanged();
    }
}
