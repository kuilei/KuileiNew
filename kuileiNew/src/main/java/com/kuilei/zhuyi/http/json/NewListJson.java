package com.kuilei.zhuyi.http.json;

import android.content.Context;

import com.kuilei.zhuyi.bean.ImagesModle;
import com.kuilei.zhuyi.bean.NewModle;
import com.kuilei.zhuyi.utils.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovog on 2016/7/10.
 */
public class NewListJson extends JsonPacket {
    private final Class TAG = NewListJson.class;
    public static NewListJson newListJson;

    public List<NewModle> newModles;

    public NewListJson(Context context) {
        super(context);
    }
    public static NewListJson instance(Context context) {
        if (newListJson == null){
            newListJson = new NewListJson(context);
        }
        return newListJson;
    }

    public List<NewModle> readJsonNewModles(String res, String value) {
        Logger.w(TAG,"readJsonNewModles");
        newModles = new ArrayList<NewModle>();
        if (res == null || res.equals("")) {
            return null;
        }
        NewModle newModle = null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(res);
            JSONArray jsonArray = jsonObject.getJSONArray(value);

            for (int i = 1; i < jsonArray.length(); i++) {
                newModle = new NewModle();
                JSONObject js = jsonArray.getJSONObject(i);
                if (js.has("skipType") && js.getString("skipType").equals("special")) {
                    continue;
                }
                if (js.has("TAGS") && !js.has("TAG")) {
                    continue;
                }
                if (js.has("imgextra")){
                    newModle.setTitle(getString("title", js));
                    newModle.setDocid(getString("docid", js));
                    ImagesModle imagesModle = new ImagesModle();
                    List<String> list;
                    list = readImgList(js.getJSONArray("imgextra"));
                    list.add(getString("imgsrc", js));
                    imagesModle.setImgList(list);
                    newModle.setImagesModle(imagesModle);
                } else {
                    newModle = readNewModle(js);
                }
                newModles.add(newModle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
        return newModles;
    }
    /**
     * 获取图文列表
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    private NewModle readNewModle(JSONObject jsonObject) throws Exception {
        NewModle newModle = null;

        String docid = "";
        String title = "";
        String digest = "";
        String imgsrc = "";
        String source = "";
        String ptime = "";
        String tag = "";

        docid = getString("docid", jsonObject);
        title = getString("title", jsonObject);
        digest = getString("digest", jsonObject);
        imgsrc = getString("imgsrc", jsonObject);
        source = getString("source", jsonObject);
        ptime = getString("ptime", jsonObject);
        tag = getString("TAG", jsonObject);

        newModle = new NewModle();

        newModle.setDigest(digest);
        newModle.setDocid(docid);
        newModle.setImgsrc(imgsrc);
        newModle.setTitle(title);
        newModle.setPtime(ptime);
        newModle.setSource(source);
        newModle.setTag(tag);

        return newModle;
    }
    /**
     * 解析图片集
     *
     * @param jsonArray
     * @return
     * @throws Exception
     */
    private List<String> readImgList(JSONArray jsonArray) throws Exception {
        List<String> imgList = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            imgList.add(getString("imgsrc", jsonArray.getJSONObject(i)));
        }

        return imgList;
    }
}
