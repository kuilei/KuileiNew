package com.kuilei.zhuyi.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.CardsAnimationAdapter;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.http.Url;
import com.kuilei.zhuyi.http.json.NewListJson;
import com.kuilei.zhuyi.initview.InitView;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.swiptlistview.SwipeListView;
import com.kuilei.zhuyi.webget.viewimage.animations.SliderLayout;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.BaseSliderView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by lenovog on 2016/11/13.
 */
@EFragment(R.layout.activity_main)
public class NBAFragment  extends BaseMainFragment implements SwipeRefreshLayout.OnRefreshListener {
    protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;

    private int index = 0;
    private boolean isRefresh = false;


    @AfterInject
    protected void init() {
        super.init();
    }

    @AfterViews
    protected void initView() {
        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
        mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);
        mListView.addHeaderView(headView);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(newAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        loadData(getCommonUrl(index + "", Url.NBAId));
        mListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                index = index + 20;
                loadData(getCommonUrl(index + "", Url.NBAId));
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
            String result = getMyActivity().getCacheStr(this.getClass().getSimpleName() + currentPagte);
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }



    @UiThread
    public void getResult(String result) {
        getMyActivity().setCacheStr(this.getClass().getSimpleName() + currentPagte, result);
        if (isRefresh) {
            isRefresh = false;
            newAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);

        List<NewModle> list = NewListJson.instance(getActivity()).readJsonNewModles(result,
                Url.NBAId);
        if (index == 0 && list.size() >= 4) {
            try {
                initSliderLayout(list,mDemoSlider,this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            newAdapter.appendList(list);
        }
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }

    @Override
    public void lazyLoad() {


    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}