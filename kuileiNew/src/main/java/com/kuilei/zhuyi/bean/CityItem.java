package com.kuilei.zhuyi.bean;

import com.kuilei.zhuyi.webget.viewimage.city.ContactItemInterface;

/**
 * Created by lenovog on 2016/11/5.
 */
public class CityItem implements ContactItemInterface {

    private String nickName;
    private String fullName;

    public CityItem(String nickName, String fullName)
    {
        super();
        this.nickName = nickName;
        this.setFullName(fullName);
    }

    @Override
    public String getItemForIndex() {
        return fullName;
    }

    @Override
    public String getDisplayInfo() {
        return nickName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
}
