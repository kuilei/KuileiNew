package com.kuilei.zhuyi;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.kuilei.zhuyi.db.SQLHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by lenovog on 2016/10/27.
 */
public class App extends Application {
    private static App mApplication;
    private SQLHelper mSQLHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    initImageLoader(this);
    }

    public static App getApp() {
        return mApplication;
    }

    public  SQLHelper getSQLHelper() {
        if (mSQLHelper == null) {
            mSQLHelper = new SQLHelper(mApplication);
        }
        return mSQLHelper;
    }


    /** 初始化ImageLoader */
    public static void initImageLoader(Context context) {
        String filePath = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName() + "/cache/";

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, filePath);
        Log.d("cacheDir",cacheDir.getParent());

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
