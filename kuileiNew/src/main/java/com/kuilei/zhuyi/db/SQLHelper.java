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

    public static final String TABLE_CHANNEL = "ChannelItem";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ORDERID = "orderId";
    public static final String SELECTED = "selected";

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
            String sql = "create table if not exists " + TABLE_CHANNEL + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID + " INTEGER , " + NAME + " TEXT, " + ORDERID + "INTEGER, " + SELECTED + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
