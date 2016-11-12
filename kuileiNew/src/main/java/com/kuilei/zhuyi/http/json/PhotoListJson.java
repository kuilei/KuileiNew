package com.kuilei.zhuyi.http.json;

import android.content.Context;

import com.kuilei.zhuyi.bean.PhotoModle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/11/8.
 */
public class PhotoListJson extends JsonPacket {
    private final Class TAG = PhotoListJson.class;

    public List<PhotoModle> photoModles = new ArrayList<PhotoModle>();

    public static PhotoListJson photoListJson;
    /**
     * @param context
     */
    public PhotoListJson(Context context) {
        super(context);
    }

    public static PhotoListJson instance(Context context) {
        if (photoListJson == null) {
            photoListJson = new PhotoListJson(context);
        }
        return photoListJson;
    }

    public List<PhotoModle> readJsonPhotoListModles(String res) {
        photoModles.clear();
        try {
            if (res == null || res.equals("")) {
                return null;
            }
            PhotoModle photoModle = null;
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                photoModle = readJsonPhotoModle(jsonArray.getJSONObject(i));
                photoModles.add(photoModle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoModles;
    }

    public String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

    private PhotoModle readJsonPhotoModle(JSONObject jsonObject) throws Exception {
        PhotoModle photoModle = null;
        String setid = "";
        String seturl = "";
        String clientcover = "";
        String setname = "";

        setid = getString("setid", jsonObject);
        seturl = getString("seturl", jsonObject);
        clientcover = getString("clientcover1",jsonObject);
        setname = getString("datetime",jsonObject);

        setname = setname.split(" ")[0];
        photoModle = new PhotoModle();

        photoModle.setClientcover(clientcover);
        photoModle.setSetid(setid);
        photoModle.setSetname(setname);
        photoModle.setSeturl(seturl);
        return photoModle;
    }
}
