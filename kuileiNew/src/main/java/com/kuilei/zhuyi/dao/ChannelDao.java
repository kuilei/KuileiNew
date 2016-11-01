package com.kuilei.zhuyi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.db.SQLHelper;
import com.kuilei.zhuyi.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lenovog on 2016/6/29.
 */
public class ChannelDao implements ChannelDaoInface {
    private final Class TAG = ChannelDao.class;
    private SQLHelper helper;
    private Context context;

    public ChannelDao(Context context) {
        this.context = context;
        helper = new SQLHelper(context);
    }


    @Override
    public boolean addCache(ChannelItem item) {
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try {
            database =helper.getWritableDatabase();
            database.beginTransaction();
            ContentValues values = new ContentValues();
            java.lang.Class<? extends ChannelItem> clazz = item.getClass();

            String tableName = clazz.getSimpleName();
            Logger.w(TAG, " tableName = " + tableName);
            Method[] methods = clazz.getMethods();

            for (Method method: methods) {
                String name = method.getName();
                if (name.startsWith("get") && ! name.startsWith("getClass")) {
                    String fieldName = name.substring(3, name.length()).toLowerCase();
                    Object value = (Objects) method.invoke(item, null);
                    if (value instanceof String) {
                        values.put(fieldName,(String)value);
                    }
                }
            }
            id = database.insert(tableName, null, values);
            flag = (id != -1 ? true : false);
            database.setTransactionSuccessful();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean deleteCache(String whereClause, String[] whereArgs) {
        return false;
    }

    @Override
    public boolean updateCache(ContentValues values, String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            database.execSQL("update" + SQLHelper.TABLE_CHANNEL + " set selected = " + values.getAsString("selected") + " where id ="
                    + values.getAsString("id"));
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (database != null) {
                database.endTransaction();
                database.close();
            }
        }
        return flag;
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
        String sql = "DELETE FROM " + SQLHelper.TABLE_CHANNEL + ";";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
        revertSeq();
    }

    private void revertSeq() {
        String sql = "update sqlite_sequence set seq=0 where name='" + SQLHelper.TABLE_CHANNEL +"'";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
    }
}
