package com.kuilei.zhuyi.webget.viewimage.city;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by lenovog on 2016/11/5.
 */
public class ContactListAdapter extends ArrayAdapter<ContactItemInterface> {

    private final int resource; // store the resource layout id for 1 row
    private boolean inSearchMode = false;

    private ContactsSectionIndexer indexer = null;
    public ContactListAdapter(Context context, int resource, List<ContactItemInterface> items) {
        super(context, resource,items);
        this.resource = resource;
        Collections.sort(items,new ContactItemComparator());
        setIndexer(new ContactsSectionIndexer(items));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;
        ContactItemInterface itemInterface = getItem(position);
        if (convertView == null) {
                parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource,parentView,true);
        } else {
            parentView = (LinearLayout) convertView;
        }

        showSectionViewIfFirstItem(parentView, itemInterface, position);

        populateDataForRow(parentView, itemInterface, position);

        return parentView;
    }

    // do all the data population for the row here
    // subclass overwrite this to draw more items
    public void populateDataForRow(View parentView, ContactItemInterface item,
                                   int position)
    {
        // default just draw the item only
        View infoView = parentView.findViewById(R.id.infoRowContainer);
        TextView nameView = (TextView) infoView.findViewById(R.id.cityName);
        nameView.setText(item.getItemForIndex());
    }

    // get the section textview from row view
    // the section view will only be shown for the first item
    public TextView getSectionTextView(View rowView)
    {
        TextView sectionTextView = (TextView) rowView
                .findViewById(R.id.sectionTextView);
        return sectionTextView;
    }

    public void showSectionViewIfFirstItem(View rowView,
                                           ContactItemInterface item, int position){
        TextView sectionTextView = getSectionTextView(rowView);

        if (inSearchMode) {
            sectionTextView.setVisibility(View.GONE);
        } else {
            if (indexer.isFirstItemInSection(position)) {
                String sectionTitle = indexer.getSectionTitle(item.getItemForIndex());
                sectionTextView.setText(sectionTitle);
                sectionTextView.setVisibility(View.VISIBLE);
            } else {
                sectionTextView.setVisibility(View.GONE);
            }
        }
    }

    public boolean isInSearchMode()
    {
        return inSearchMode;
    }

    public void setInSearchMode(boolean inSearchMode)
    {
        this.inSearchMode = inSearchMode;
    }

    public ContactsSectionIndexer getIndexer()
    {
        return indexer;
    }

    public void setIndexer(ContactsSectionIndexer indexer)
    {
        this.indexer = indexer;
    }
}
