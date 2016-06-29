package com.kuilei.zhuyi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovog on 2016/6/29.
 */
public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";// 数据库名称
    public static final int VERSION = 1;

    private final Context context;

    public SQLHelper(Context context) {
        super(context,DB_NAME, null, VERSION);
        this.context = context;
    }


    public Context getContext()
    {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
