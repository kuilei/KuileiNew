package com.kuilei.zhuyi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kuilei.zhuyi.bean.VideoModle;
import com.kuilei.zhuyi.view.VideoItemView;
import com.kuilei.zhuyi.view.VideoItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/12.
 */
@EBean
public class VideoAdapter extends BaseAdapter {
    public List<VideoModle> lists = new ArrayList<VideoModle>();

    public void appendList(List<VideoModle> list) {
        if (!lists.containsAll(list) && list != null && list.size() > 0) {
            lists.addAll(list);
        }
        notifyDataSetChanged();
    }

    @RootContext
    Context context;

    public void clear() {
        lists.clear();
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

        VideoItemView videoItemView;

        if (convertView == null) {
            videoItemView = VideoItemView_.build(context);
        } else {
            videoItemView = (VideoItemView) convertView;
        }

        VideoModle videoModle = lists.get(position);
        videoItemView.setData(videoModle);

        return videoItemView;
    }
}
