package com.kuilei.zhuyi.dao;

import android.content.ContentValues;
import android.content.Context;

import com.kuilei.zhuyi.bean.ChannelItem;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovog on 2016/6/29.
 */
public class ChannelDao implements ChannelDaoInface {

    public ChannelDao() {
    }

    public ChannelDao(Context context) {

    }

    @Override
    public boolean addCache(ChannelItem item) {
        return false;
    }

    @Override
    public boolean deleteCache(String whereClause, String[] whereArgs) {
        return false;
    }

    @Override
    public boolean updateCache(ContentValues values, String whereClause, String[] whereArgs) {
        return false;
    }

    @Override
    public Map<String, String> viewCache(String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public List<Map<String, String>> listCache(String selection, String[] selectionArgs) {
        return null;
    }

    @Override
    public void clearFeedTable() {

    }
}
