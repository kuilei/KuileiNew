package com.kuilei.zhuyi.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.CityAdapter;
import com.kuilei.zhuyi.adapter.CityData;
import com.kuilei.zhuyi.bean.CityItem;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.webget.viewimage.city.ContactItemInterface;
import com.kuilei.zhuyi.webget.viewimage.city.ContactListViewImpl;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/5.
 */
@EActivity(R.layout.activity_choose_city)
public class ChooseActivity extends BaseActivity {
    private final  Class TAG = ChooseActivity.class;

    @ViewById(R.id.listview)
    protected ContactListViewImpl listview;

    @ViewById(R.id.title)
    protected TextView mTitle;

    @ViewById(R.id.input_search_query)
    protected EditText searchBox;
    private String searchString;

    private Context context;
    private Object searchLock;
    List<ContactItemInterface> contactList;
    List<ContactItemInterface> filterList;
    private CityAdapter adapter;

    private SearchListTask curSearchTask = null;

    boolean inSearchMode = false;

    @AfterInject
    public void init() {
        context = this;
        searchLock = new Object();
        contactList = CityData.getSampleContactList();
        filterList = new ArrayList<ContactItemInterface>();
        Logger.w(TAG, "contactList =" + contactList.size());
        adapter = new CityAdapter(this, R.layout.city_item, contactList);
    }

    @AfterViews
    public void initView() {
        Logger.w(TAG, "initView");
        listview.setFastScrollEnabled(true);
        listview.setAdapter(adapter);
        mTitle.setText("选择城市");
    }


    @AfterTextChange(R.id.input_search_query)
    public void afterTextChanged(Editable s) {
        searchString = searchBox.getText().toString().trim().toUpperCase();

        if (curSearchTask != null && curSearchTask.getStatus() != AsyncTask.Status.FINISHED) {
            try
            {
                curSearchTask.cancel(true);
            } catch (Exception e)
            {
                Logger.i(TAG, "Fail to cancel running search task");
            }
        }
        curSearchTask = new SearchListTask();
        curSearchTask.execute(searchString);
    }


    private class SearchListTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            filterList.clear();

            String keyWord = params[0];

            inSearchMode = (keyWord.length() > 0);

            if (inSearchMode) {
                for (ContactItemInterface itemInterface: contactList) {
                    CityItem cityItem = (CityItem) itemInterface;
                    boolean isPinyin = cityItem.getFullName().toUpperCase().indexOf(keyWord) > -1;
                    boolean isChinese = cityItem.getNickName().indexOf(keyWord) > -1;
                    if (isChinese || isPinyin) {
                        filterList.add(cityItem);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            synchronized (searchLock) {
                if (inSearchMode) {
                    CityAdapter adapter = new CityAdapter(context, R.layout.city_item, filterList);
                    adapter.setInSearchMode(true);

                    listview.setAdapter(adapter);
                } else {
                    CityAdapter adapter = new CityAdapter(context, R.layout.city_item, contactList);
                    adapter.setInSearchMode(false);

                    listview.setAdapter(adapter);
                }
            }
        }
    }
}
