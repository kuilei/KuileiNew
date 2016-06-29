package com.kuilei.zhuyi.bean;

import com.kuilei.zhuyi.dao.ChannelDao;
import com.kuilei.zhuyi.db.SQLHelper;
import com.kuilei.zhuyi.utils.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by lenovog on 2016/6/29.
 */
public class ChannelManage {
    public static ChannelManage channelManage;


    public static List<ChannelItem> defaultUserChannels;

    public static List<ChannelItem> defaultOtherChannels;

    private ChannelDao channelDao;

    private boolean userExist = false;

    private ChannelManage(SQLHelper paramDBHelper) throws SQLException
    {
        if (channelDao == null)
            channelDao = new ChannelDao(paramDBHelper.getContext());

        initDefaultChannel();
        return;
    }

    public static ChannelManage getManage(SQLHelper dbHelper) throws SQLException
    {
        if (channelManage == null)
        {
            synchronized (ChannelManage.class)
            {
                if (channelManage == null)
                {
                    channelManage = new ChannelManage(dbHelper);
                }
            }
        }
        return channelManage;
    }

    /*
    初始化数据库内的频道数据
     */
    private void initDefaultChannel() {
        Logger.w(ChannelManage.class,"deleteAll Channel");
        deleteAllChannel();
        saveUserChannel(defaultUserChannels);
        saveOtherChannel(defaultOtherChannels);

    }

    private void saveOtherChannel(List<ChannelItem> defaultOtherChannels) {
        for (int i = 0; i < defaultOtherChannels.size(); i++)
        {
            ChannelItem channelItem = defaultOtherChannels.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(0));
            channelDao.addCache(channelItem);
        }
    }

    private void saveUserChannel(List<ChannelItem> defaultUserChannels) {
        for (int i = 0; i < defaultUserChannels.size(); i++)
        {
            ChannelItem channelItem = defaultUserChannels.get(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(Integer.valueOf(1));
            channelDao.addCache(channelItem);
        }
    }

    private void deleteAllChannel() {

    }
}
