package com.kuilei.zhuyi.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kuilei.zhuyi.bean.WeatherModle;
import com.kuilei.zhuyi.view.WeatherItemView;
import com.kuilei.zhuyi.view.WeatherItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/3.
 */
@EBean
public class WeatherAdapter extends BaseAdapter {

    public List<WeatherModle> lists = new ArrayList<WeatherModle>();

    @RootContext
    Context context;

    public void appendList(List<WeatherModle> list) {
        if (!lists.containsAll(list) && list != null && list.size() > 0) {
            lists.addAll(list);
        }
        notifyDataSetChanged();
    }

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
        WeatherItemView weatherItemView;
        if (convertView == null) {
            weatherItemView = WeatherItemView_.build(context);
        } else {
            weatherItemView = (WeatherItemView) convertView;
        }
        weatherItemView.setData(lists.get(position));
        return weatherItemView;
    }
}
