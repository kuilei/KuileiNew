package com.kuilei.zhuyi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kuilei.zhuyi.bean.ChannelItem;
import com.kuilei.zhuyi.db.SQLHelper;
import com.kuilei.zhuyi.utils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    Object value = method.invoke(item, null);
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
            database.execSQL("update " + SQLHelper.TABLE_CHANNEL + " set selected = " + values.getAsString("selected") + " where id = "
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
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = helper.getWritableDatabase();
            database.beginTransaction();
            cursor = database.query(false, SQLHelper.TABLE_CHANNEL, null, selection, selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            Logger.w(TAG," getColumnCount = " + cols_len );
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor.getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.endTransaction();
                cursor.close();
                database.close();
            }
        }
        return list;
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
