package com.example.lzp.ganhuo.util;

import android.util.Log;

import com.example.lzp.ganhuo.fragment.today.Today;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKJP on 2017/3/9.
 */

public class JsonParser {
    public static Today parse2Today(String s) {
        Today today = new Today();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray categorys = jsonObject.getJSONArray("category");
            JSONObject results = jsonObject.getJSONObject("results");

            List<Today.Results.Item> androidList = new ArrayList<>();
            List<Today.Results.Item> iosList = new ArrayList<>();
            List<Today.Results.Item> videoList = new ArrayList<>();
            List<Today.Results.Item> webList = new ArrayList<>();
            List<Today.Results.Item> resList = new ArrayList<>();
            List<Today.Results.Item> fuliList = new ArrayList<>();

            JSONArray androids = results.getJSONArray("Android");
            int aLen = androids.length();
            for (int a = 0; a < aLen; a++) {
                JSONObject jsobj = androids.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item android = new Today.Results.Item();
                android.set_id(_id);
                android.setCreatedAt(createdAt);
                android.setDesc(desc);
                android.setPublishedAt(publishedAt);
                android.setSource(source);
                android.setType(type);
                android.setUrl(url);
                android.setUsed(used);
                android.setWho(who);

                androidList.add(android);
            }

            JSONArray ioss = results.getJSONArray("iOS");
            int iLen = ioss.length();
            for (int a = 0; a < iLen; a++) {
                JSONObject jsobj = ioss.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item ios = new Today.Results.Item();
                ios.set_id(_id);
                ios.setCreatedAt(createdAt);
                ios.setDesc(desc);
                ios.setPublishedAt(publishedAt);
                ios.setSource(source);
                ios.setType(type);
                ios.setUrl(url);
                ios.setUsed(used);
                ios.setWho(who);

                iosList.add(ios);
            }

            JSONArray videos = results.getJSONArray("休息视频");
            int vLen = videos.length();
            for (int a = 0; a < vLen; a++) {
                JSONObject jsobj = videos.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item video = new Today.Results.Item();
                video.set_id(_id);
                video.setCreatedAt(createdAt);
                video.setDesc(desc);
                video.setPublishedAt(publishedAt);
                video.setSource(source);
                video.setType(type);
                video.setUrl(url);
                video.setUsed(used);
                video.setWho(who);

                videoList.add(video);
            }

            JSONArray webs = results.getJSONArray("前端");
            int wLen = videos.length();
            for (int a = 0; a < wLen; a++) {
                JSONObject jsobj = webs.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item web = new Today.Results.Item();
                web.set_id(_id);
                web.setCreatedAt(createdAt);
                web.setDesc(desc);
                web.setPublishedAt(publishedAt);
                web.setSource(source);
                web.setType(type);
                web.setUrl(url);
                web.setUsed(used);
                web.setWho(who);

                webList.add(web);
            }

            JSONArray ress = results.getJSONArray("拓展资源");
            int rLen = ress.length();
            for (int a = 0; a < rLen; a++) {
                JSONObject jsobj = ress.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item res = new Today.Results.Item();
                res.set_id(_id);
                res.setCreatedAt(createdAt);
                res.setDesc(desc);
                res.setPublishedAt(publishedAt);
                res.setSource(source);
                res.setType(type);
                res.setUrl(url);
                res.setUsed(used);
                res.setWho(who);

                resList.add(res);
            }

            JSONArray fulis = results.getJSONArray("福利");
            int fLen = ress.length();
            for (int a = 0; a < fLen; a++) {
                JSONObject jsobj = fulis.getJSONObject(a);
                String _id = jsobj.getString("_id");
                String createdAt = jsobj.getString("createdAt");
                String desc = jsobj.getString("desc");
                String publishedAt = jsobj.getString("publishedAt");
                String source = jsobj.getString("source");
                String type = jsobj.getString("type");
                String url = jsobj.getString("url");
                String used = jsobj.getString("used");
                String who = jsobj.getString("who");
                Today.Results.Item fuli = new Today.Results.Item();
                fuli.set_id(_id);
                fuli.setCreatedAt(createdAt);
                fuli.setDesc(desc);
                fuli.setPublishedAt(publishedAt);
                fuli.setSource(source);
                fuli.setType(type);
                fuli.setUrl(url);
                fuli.setUsed(used);
                fuli.setWho(who);

                fuliList.add(fuli);
            }
            Today.Results results1 = new Today.Results();
            results1.setAndroids(androidList);
            results1.setIoss(iosList);
            results1.setFulis(fuliList);
            results1.setResources(resList);
            results1.setVideos(videoList);
            results1.setWebs(webList);

            today.setResults(results1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return today;
    }
}
