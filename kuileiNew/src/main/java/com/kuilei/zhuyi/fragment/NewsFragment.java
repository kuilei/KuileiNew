package com.kuilei.zhuyi.fragment;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.CardsAnimationAdapter;
import com.kuilei.zhuyi.adapter.NewAdapter;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.http.Url;
import com.kuilei.zhuyi.http.json.NewListJson;
import com.kuilei.zhuyi.initview.InitView;
import com.kuilei.zhuyi.utils.HttpUtil;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.webget.swiptlistview.SwipeListView;
import com.kuilei.zhuyi.webget.viewimage.animations.SliderLayout;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.BaseSliderView;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.TextSliderView;
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
 * Created by lenovog on 2016/6/29.
 */
@EFragment(R.layout.activity_main)
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseSliderView.OnSliderClickListener {
    private final Class TAG = NewsFragment.class;
    protected SliderLayout mDemoSlider;
    @ViewById(R.id.swipe_container)
    protected SwipeRefreshLayout swipeLayout;
    @ViewById(R.id.listview)
    protected SwipeListView mListView;
    @ViewById(R.id.progressBar)
    protected ProgressBar mProgressBar;
    protected HashMap<String, String> url_maps;

    protected HashMap<String, NewModle> newHashMap;

    @Bean
    protected NewAdapter newAdapter;
    protected List<NewModle> listsModles;
    private int index = 0;
    private boolean isRefresh = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @AfterInject
    protected void init() {

        listsModles = new ArrayList<NewModle>();
        url_maps = new HashMap<String, String>();

        newHashMap = new HashMap<String, NewModle>();
    }

    @AfterViews
    protected void initView() {
        Logger.w(NewsFragment.class,"initView");
        swipeLayout.setOnRefreshListener(this);
        InitView.instance().initSwipeRefreshLayout(swipeLayout);
        InitView.instance().initListView(mListView, getActivity());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_item, null);
        mDemoSlider = (SliderLayout) headView.findViewById(R.id.slider);
        mListView.addHeaderView(headView);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(newAdapter);
        animationAdapter.setAbsListView(mListView);
        mListView.setAdapter(animationAdapter);
        loadData(getNewUrl(index + ""));

        mListView.setOnBottomListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPagte++;
                index = index + 20;
                loadData(getNewUrl(index + ""));
            }
        });
    }

    private void loadData(String url) {
        if (getMyActivity().hasNetWork()) {
            loadNewList(url);
          //  getMyActivity().showShortToast("show information");
        } else {
            mListView.onBottomComplete();
            mProgressBar.setVisibility(View.GONE);
            getMyActivity().showShortToast(getString(R.string.not_network));
       //     String result = getMyActivity().getCacheStr("NewsFragment" + currentPagte);
        //    if (!StringUtils.isEmpty(result)) {
        //        getResult(result);
        //    }
        }
    }



    @Background
    void loadNewList(String url) {
        String result;
        try {
            result = HttpUtil.getByHttpClient(getActivity(), url);
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
        getMyActivity().setCacheStr("NewsFragment" + currentPagte, result);
        if (isRefresh)
        {
            isRefresh = false;
            newAdapter.clear();
            listsModles.clear();
        }
        mProgressBar.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
        List<NewModle> list = NewListJson.instance(getActivity()).readJsonNewModles(result, Url.TopId);
        if (index == 0 && list.size() >= 4) {
            initSliderLayout(list);
           // getMyActivity().showShortToast("initSliderLayout");
        } else {
            newAdapter.appendList(list);
        }
        listsModles.addAll(list);
        mListView.onBottomComplete();
    }

    private void initSliderLayout(List<NewModle> newModles) {

        if (!isNullString(newModles.get(0).getImgsrc()))
            newHashMap.put(newModles.get(0).getImgsrc(), newModles.get(0));
        if (!isNullString(newModles.get(1).getImgsrc()))
            newHashMap.put(newModles.get(1).getImgsrc(), newModles.get(1));
        if (!isNullString(newModles.get(2).getImgsrc()))
            newHashMap.put(newModles.get(2).getImgsrc(), newModles.get(2));
        if (!isNullString(newModles.get(3).getImgsrc()))
            newHashMap.put(newModles.get(3).getImgsrc(), newModles.get(3));

        if (!isNullString(newModles.get(0).getImgsrc()))
            url_maps.put(newModles.get(0).getTitle(), newModles.get(0).getImgsrc());
        if (!isNullString(newModles.get(1).getImgsrc()))
            url_maps.put(newModles.get(1).getTitle(), newModles.get(1).getImgsrc());
        if (!isNullString(newModles.get(2).getImgsrc()))
            url_maps.put(newModles.get(2).getTitle(), newModles.get(2).getImgsrc());
        if (!isNullString(newModles.get(3).getImgsrc()))
            url_maps.put(newModles.get(3).getTitle(), newModles.get(3).getImgsrc());

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.setOnSliderClickListener(this);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name));

            textSliderView.getBundle()
                    .putString("extra", name);
            Logger.w(TAG,"textSliderView="+textSliderView);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
       // mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        newAdapter.appendList(newModles);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
