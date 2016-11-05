package com.kuilei.zhuyi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.webget.viewimage.city.ContactItemInterface;
import com.kuilei.zhuyi.webget.viewimage.city.ContactListAdapter;

import java.util.List;

/**
 * Created by lenovog on 2016/11/5.
 */
public class CityAdapter extends ContactListAdapter {
    public CityAdapter(Context context, int resource, List<ContactItemInterface> items) {
        super(context, resource, items);
    }

    @Override
    public void populateDataForRow(View parentView, ContactItemInterface item,
                                   int position)
    {
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nicknameView = (TextView) infoView
                .findViewById(R.id.cityName);

        nicknameView.setText(item.getDisplayInfo());
    }

}
