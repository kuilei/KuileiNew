package com.kuilei.zhuyi.webget.viewimage.city;

import java.util.Comparator;

/**
 * Created by lenovog on 2016/11/5.
 */
public class ContactItemComparator implements Comparator<ContactItemInterface> {
    @Override
    public int compare(ContactItemInterface lhs, ContactItemInterface rhs) {
        if (lhs.getItemForIndex() == null || rhs.getItemForIndex() == null)
            return -1;

        return (lhs.getItemForIndex().compareTo(rhs.getItemForIndex()));

    }
}
