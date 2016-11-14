package com.kuilei.zhuyi.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.CardsAnimationAdapter;
import com.kuilei.zhuyi.adapter.PhotoAdapter;
import com.kuilei.zhuyi.bean.PhotoModle;
import com.kuilei.zhuyi.http.json.PhotoListJson;
import com.kuilei.zhuyi.initview.InitView;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.utils.OkHttpUtil;
import com.kuilei.zhuyi.utils.PreferencesUtils;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.swiptlistview.SwipeListView;
import com.kuilei.zhuyi.webget.viewimage.animations.SliderLayout;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.BaseSliderView;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovog on 2016/11/8.
 */
@EFragment(R.layout.activity_main)
public class PictureReDianFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseSliderView.OnSliderClickListener {

    private final Class TAG = PictureReDianFragment.class;

    protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;
    protected HashMap<String, String> url_maps;

    protected HashMap<String, PhotoModle> newHashMap;

    public int indexId;

    public int index;

    public int count = 0;

    public long lastupdatetimeredian;

    @Bean
    protected PhotoAdapter photoAdapter;
    protected List<PhotoModle> listsModles;
    private boolean isRefresh = false;


    @AfterInject
    protected void init() {
        listsModles = new ArrayList<PhotoModle>();
        url_maps = new HashMap<String, String>();
        newHashMap = new HashMap<String, PhotoModle>();

        int count = PreferencesUtils.getInt(getActivity(), "indexredian");
        lastupdatetimeredian = PreferencesUtils.getLong(getActivity(), "lastupdatetimeredian");
        if (count != -1) {
            if ((lastupdatetimeredian + (24 * 60 * 60 * 1000)) < System.currentTimeMillis()) {
                lastupdatetimeredian = System.currentTimeMillis();
                PreferencesUtils.putLong(getActivity(), "lastupdatetimeredian",
                        lastupdatetimeredian);
                int beishu = (int) (System.currentTimeMillis() / (lastupdatetimeredian + (24 * 60 * 60 * 1000)));
                count = (count + 5) * beishu;
            } else {
                indexId = count;
            }
        } else {
            indexId = 42606;
            PreferencesUtils.putLong(getActivity(), "lastupdatetimeredian",
                    System.currentTimeMillis());
            PreferencesUtils.putInt(getActivity(), "indexredian", indexId);
        }

        index = indexId;
    }

    @AfterViews
    protected void initView() {
        Logger.w(TAG,"initView" );
        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
        mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);
        // mListView.addHeaderView(headView);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(photoAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        loadData(getPhotosUrl(indexId + ""));

        mListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                indexId = indexId - 10;
                loadData(getPhotosUrl(indexId + ""));
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
        }
    }


    @Background
    void loadNewList(String url) {
        Logger.w(TAG,"loadNewList =" + url );
        String result;
        try {
            result = OkHttpUtil.getAsString(url);
            Logger.w(TAG,"result = " + result);
            if (!StringUtils.isEmpty(result)) {

                getResult(result);
            } else {
                swipeLayout.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void getResult(String result) {
        //设置缓存
        getMyActivity().setCacheStr("PictureReDianFragment" + currentPagte, result);
        if (isRefresh)
        {
            isRefresh = false;
            photoAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
        //解析数据
        List<PhotoModle> list = PhotoListJson.instance(getActivity()).readJsonPhotoListModles(result);
            photoAdapter.appendList(list);
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPagte = 1;
                isRefresh = true;
                index = index + 5;
                loadData(getPhotosUrl(index + ""));
                url_maps.clear();
                mDemoSlider.removeAllSliders();
                PreferencesUtils.putInt(getActivity(), "indexredian", index);
            }
        }, 2000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
