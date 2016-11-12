package com.kuilei.zhuyi.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.activity.BaseActivity;
import com.kuilei.zhuyi.activity.VideoPlayActivity_;
import com.kuilei.zhuyi.adapter.CardsAnimationAdapter;
import com.kuilei.zhuyi.adapter.VideoAdapter;
import com.kuilei.zhuyi.bean.VideoModle;
import com.kuilei.zhuyi.http.Url;
import com.kuilei.zhuyi.http.json.ViedoListJson;
import com.kuilei.zhuyi.initview.InitView;
import com.kuilei.zhuyi.utils.OkHttpUtil;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.swiptlistview.SwipeListView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/12.
 */
@EFragment(R.layout.activity_main)
public class VideoReDianFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {



    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;

    @Bean
    protected VideoAdapter videoAdapter;
    protected List<VideoModle> listsModles;
    private int index = 0;
    private boolean isRefresh = false;



    @AfterInject
    protected void init() {
        listsModles = new ArrayList<VideoModle>();
    }

    @AfterViews
    protected void initView() {

        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(videoAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        loadData(getVideoUrl(index + "", Url.VideoReDianId));

        mListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                index = index + 10;
                loadData(getVideoUrl(index + "", Url.VideoReDianId));
            }
        });
    }

    private void loadData(String url) {
        if (getMyActivity().hasNetWork()) {
            loadNewList(url);
        } else {
            mListView.onBottomComplete();
            mProgressBar.setVisibility(View.GONE);
            getMyActivity().showShortToast(getString(R.string.not_network));
            String result = getMyActivity().getCacheStr("VideoReDianFragment" + currentPagte);
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }


    @Background
    void loadNewList(String url) {
        String result;
        try {
            result = OkHttpUtil.getAsString(url);
            getResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getResult(String result) {
        getMyActivity().setCacheStr("VideoReDianFragment" + currentPagte, result);
        if (isRefresh) {
            isRefresh = false;
            videoAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);

        List<VideoModle> list =
                ViedoListJson.instance(getActivity()).readJsonVideoModles(result,
                        Url.VideoReDianId);
        videoAdapter.appendList(list);
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }


    @ItemClick(R.id.listview)
    protected void onItemClick(int position) {
        VideoModle videoModle = listsModles.get(position);
        enterDetailActivity(videoModle);
    }

    public void enterDetailActivity(VideoModle videoModle) {
        Bundle bundle = new Bundle();
        bundle.putString("playUrl", videoModle.getMp4Hd_url());
        bundle.putString("filename", videoModle.getTitle());
        ((BaseActivity) getActivity()).openActivity(VideoPlayActivity_.class, bundle, 0);
    }

    @Override
    public void onRefresh() {

    }


}
