package com.kuilei.zhuyi.http.json;

import android.content.Context;

import com.kuilei.zhuyi.bean.WeatherModle;
import com.kuilei.zhuyi.utils.Logger;
import com.kuilei.zhuyi.utils.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/3.
 */
public class WeatherListJson extends JsonPacket {
    private final Class TAG = WeatherListJson.class;
    public List<WeatherModle> weatherListModles = new ArrayList<WeatherModle>();

    public static WeatherListJson weatherListJson;

    public WeatherListJson(Context context) {
        super(context);
    }

    public static WeatherListJson instance(Context context) {
        if (weatherListJson == null) {
            weatherListJson = new WeatherListJson(context);
        }

        return weatherListJson;
    }

    public List<WeatherModle> readJsonWeatherListModles(String res) {
        Logger.w(TAG,"readJsonWeatherListModles");
        weatherListModles.clear();
        try {
            if (res == null || res.equals("")) {
                return null;
            }
            WeatherModle weatherModle = null;
            JSONObject jsonObject = new JSONObject(res);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("forecast");
            for (int i = 0; i < jsonArray.length(); i++) {
                weatherModle = readJsonWeatherModles(jsonArray.getJSONObject(i));
                weatherListModles.add(weatherModle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
        return weatherListModles;
    }

    private WeatherModle readJsonWeatherModles(JSONObject jsonObject) throws Exception {
        WeatherModle weatherModle = null;

        String temperature = "";
        String weather = "";
        String wind = "";
        String date = "";

        temperature = getString("high", jsonObject) + " " + getString("low", jsonObject);
        weather = getString("type", jsonObject);
        wind = getString("fengxiang", jsonObject);
        date = getString("date", jsonObject);

        weatherModle = new WeatherModle();

        weatherModle.setDate(TimeUtils.getCurrentTime() + date);
        weatherModle.setTemperature(temperature);
        weatherModle.setWeather(weather);
        weatherModle.setWeek("");
        weatherModle.setWind(wind);

        return weatherModle;
    }

}
