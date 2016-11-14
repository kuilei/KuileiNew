package com.kuilei.zhuyi.fragment;

import com.kuilei.zhuyi.adapter.NewAdapter;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.utils.OkHttpUtil;
import com.kuilei.zhuyi.webget.viewimage.animations.DescriptionAnimation;
import com.kuilei.zhuyi.webget.viewimage.animations.SliderLayout;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.BaseSliderView;
import com.kuilei.zhuyi.webget.viewimage.slidertypes.TextSliderView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovog on 2016/11/13.
 */
@EFragment
public abstract class BaseMainFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener{

    protected HashMap<String, String> url_maps;

    protected HashMap<String, NewModle> newHashMap;

    private String mUrl;

    public boolean isInitView = false;

    @Bean
    protected NewAdapter newAdapter;
    protected List<NewModle> listsModles;

    protected void init() {
        listsModles = new ArrayList<NewModle>();
        url_maps = new HashMap<String, String>();
        newHashMap = new HashMap<String, NewModle>();
    }


    protected  void initView() {
        isInitView = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    public void onVisible() {
        lazyLoad();
    }

    public void onInVisible() {
//
    }

    public void callHttp() {
        OkHttpUtil.getInstance().callHttp(mUrl);
    }

    @Background
    void loadNewList(String url) {
        mUrl = url;
        String result;
        try {
            result = OkHttpUtil.getAsString(url);
            getResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    protected void initSliderLayout(List<NewModle> newModles, SliderLayout mDemoSlider, BaseMainFragment context) {

        if (newModles.get(0) != null && !isNullString(newModles.get(0).getImgsrc()))
            newHashMap.put(newModles.get(0).getImgsrc(), newModles.get(0));
        if (newModles.get(1) != null && !isNullString(newModles.get(1).getImgsrc()))
            newHashMap.put(newModles.get(1).getImgsrc(), newModles.get(1));
        if (newModles.get(2) != null && !isNullString(newModles.get(2).getImgsrc()))
            newHashMap.put(newModles.get(2).getImgsrc(), newModles.get(2));
        if (newModles.get(3) != null && !isNullString(newModles.get(3).getImgsrc()))
            newHashMap.put(newModles.get(3).getImgsrc(), newModles.get(3));

        if (newModles.get(0) != null && !isNullString(newModles.get(0).getImgsrc()))
            url_maps.put(newModles.get(0).getTitle(), newModles.get(0).getImgsrc());
        if (newModles.get(1) != null && !isNullString(newModles.get(1).getImgsrc()))
            url_maps.put(newModles.get(1).getTitle(), newModles.get(1).getImgsrc());
        if (newModles.get(2) != null && !isNullString(newModles.get(2).getImgsrc()))
            url_maps.put(newModles.get(2).getTitle(), newModles.get(2).getImgsrc());
        if (newModles.get(3) != null && !isNullString(newModles.get(3).getImgsrc()))
            url_maps.put(newModles.get(3).getTitle(), newModles.get(3).getImgsrc());

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.setOnSliderClickListener(context);
            textSliderView
                    .description(name)
                    .image(url_maps.get(name));

            textSliderView.getBundle()
                    .putString("extra", name);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        newAdapter.appendList(newModles);
    }

    public abstract void getResult(String url);
    public abstract void lazyLoad();
}
