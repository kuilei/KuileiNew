package com.kuilei.zhuyi.activity;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuilei.zhuyi.R;
import com.kuilei.zhuyi.adapter.MyViewPagerAdapter;
import com.kuilei.zhuyi.adapter.WeatherAdapter;
import com.kuilei.zhuyi.bean.WeatherModle;
import com.kuilei.zhuyi.http.json.WeatherHandle;
import com.kuilei.zhuyi.http.json.WeatherListJson;
import com.kuilei.zhuyi.initview.SlidingMenuView;
import com.kuilei.zhuyi.utils.OkHttpUtil;
import com.kuilei.zhuyi.utils.StringUtils;
import com.kuilei.zhuyi.utils.TimeUtils;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/2.
 */
@EActivity(R.layout.activity_weather)
public class WeatherActivity extends BaseActivity implements WeatherHandle.onResponse {

    @ViewById(R.id.title)
    protected TextView mTitle;
    @ViewById(R.id.local)
    protected TextView mLocal;
    @ViewById(R.id.layout)
    protected RelativeLayout mLayout;
    @ViewById(R.id.weatherTemp)
    protected TextView mWeatherTemp;
    @ViewById(R.id.weather)
    protected TextView mWeather;
    @ViewById(R.id.wind)
    protected TextView mWind;
    @ViewById(R.id.weatherImage)
    protected ImageView mWeatherImage;
    @ViewById(R.id.week)
    protected TextView mWeek;
    @ViewById(R.id.weather_date)
    protected TextView mWeatherDate;
    @ViewById(R.id.vPager)
    protected ViewPager mViewPager;


    @Bean
    protected WeatherAdapter mWeatherAdapter1, mWeatherAdapter2;

    private List<View> views;

    private View weatherGridView1, weatherGridView2;

    private GridView view1, view2;

    private WeatherHandle weatherHandle;

    @AfterInject
    public void init() {
        views = new ArrayList<View>();
        weatherHandle = new WeatherHandle(this);
    }

    @AfterViews
    public void initView() {
        try {
            initViewPager();
            String titleName = getCacheStr("titleName");
            if (StringUtils.isEmpty(titleName)) {
                titleName = "北京";
            }
            mTitle.setText(titleName + "天气");
            mLocal.setVisibility(View.VISIBLE);
            setBack(titleName);
            loadData(getWeatherUrl(titleName));

            mWeatherDate.setText(TimeUtils.getCurrentTime());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData(String url) {
        if (hasNetWork()) {
            loadNewDetailData(url);
        } else {
            showShortToast(getString(R.string.not_network));
            String result = getCacheStr("WeatherActivity");
            if (!StringUtils.isEmpty(result)) {
                getResult(result);
            }
        }
    }


    @UiThread
    public void getResult(String result) {
        setCacheStr("WeatherActivity", result);
        List<WeatherModle> weatherModles = WeatherListJson.instance(this)
                .readJsonWeatherListModles(
                        result);
        if (weatherModles.size() > 0) {
            setWeather(weatherModles.get(0));
            mWeatherAdapter1.clear();
            mWeatherAdapter2.clear();
            mWeatherAdapter1.appendList(weatherModles.subList(1, 4));
            mWeatherAdapter2.appendList(weatherModles.subList(4, weatherModles.size()));
        } else {
            showShortToast("错误");
        }
    }

    @Click(R.id.local)
    public void chooseCity(View view) {
        ChooseActivity_.intent(this).startForResult(REQUEST_CODE);
    }

    public void setWeather(WeatherModle weatherModle) {
        mWeather.setText(weatherModle.getWeather());
        mWind.setText(weatherModle.getWind());
        mWeatherTemp.setText(weatherModle.getTemperature());
        mWeek.setText(weatherModle.getDate());
        SlidingMenuView.instance().setWeatherImage(mWeatherImage, weatherModle.getWeather());
    }


    @Background
    public void loadNewDetailData(String url) {
        OkHttpUtil.getAsyn(url,weatherHandle);
    }


    public void setBack(String cityName) {
        if (cityName.equals("北京")) {
            mLayout.setBackgroundResource(R.mipmap.biz_plugin_weather_beijin_bg);
        } else if (cityName.equals("上海")) {
            mLayout.setBackgroundResource(R.mipmap.biz_plugin_weather_shanghai_bg);
        } else if (cityName.equals("广州")) {
            mLayout.setBackgroundResource(R.mipmap.biz_plugin_weather_guangzhou_bg);
        } else if (cityName.equals("深圳")) {
            mLayout.setBackgroundResource(R.mipmap.biz_plugin_weather_shenzhen_bg);
        } else {
            mLayout.setBackgroundResource(R.mipmap.biz_news_local_weather_bg_big);
        }
    }

    private void initViewPager() {
        weatherGridView1 = LayoutInflater.from(this).inflate(R.layout.gridview_weather, null);
        weatherGridView2 = LayoutInflater.from(this).inflate(R.layout.gridview_weather, null);
        view1 = (GridView) weatherGridView1.findViewById(R.id.gridView);
        view2 = (GridView) weatherGridView2.findViewById(R.id.gridView);
        view1.setAdapter(mWeatherAdapter1);
        view2.setAdapter(mWeatherAdapter2);
        views.add(weatherGridView1);
        views.add(weatherGridView2);
        mViewPager.setOffscreenPageLimit(1);
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(views);
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(0);

    }


    @Override
    public void onResponse(String str) {
        getResult(str);
    }
}
