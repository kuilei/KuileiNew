package com.kuilei.zhuyi.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ProgressBar;

import com.kuilei.zhuyi.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

/**
 * Created by lenovog on 2016/6/29.
 */
@EFragment
public class NewsFragment extends  BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
 //   protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
   // @ViewById(R.id.listview)
  //  protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;
    protected HashMap<String, String> url_maps;

   // protected HashMap<String, NewModle> newHashMap;
    @Override
    public void onRefresh() {

    }
}
