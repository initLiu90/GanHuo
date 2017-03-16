package com.example.lzp.ganhuo.data.today;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.data.GHDbHelper;
import com.example.lzp.ganhuo.fragment.today.Today;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKJP on 2017/3/16.
 */

public class TodayLocalDataSource implements TodayDataSource {

    private static TodayLocalDataSource INSTANCE = null;

    private ContentResolver mResolver;

    private TodayLocalDataSource(Context context) {
        this.mResolver = context.getContentResolver();
    }

    public static TodayLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TodayLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getToday(String date, LoadTodayCallback callback) {
        String[] projection = {TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE,
                TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, TodayPersistenceContract
                .TodayEntry.COLUMN_NAME_URL, TodayPersistenceContract.TodayEntry
                .COLUMN_NAME_AUTHER, TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL};
        String selection = TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor
                = mResolver.query(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI, projection,
                selection, selectionArgs, null);

        Today today = new Today();
        Today.Results results = new Today.Results();
        List<Today.Results.Item> androids = new ArrayList<>();
        List<Today.Results.Item> ioss = new ArrayList<>();
        List<Today.Results.Item> videos = new ArrayList<>();
        List<Today.Results.Item> webs = new ArrayList<>();
        List<Today.Results.Item> resources = new ArrayList<>();
        List<Today.Results.Item> fulis = new ArrayList<>();
        List<Today.Results.Item> xias = new ArrayList<>();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndex(TodayPersistenceContract
                        .TodayEntry.COLUMN_NAME_TYPE));
                String desc = cursor.getString(cursor.getColumnIndex(TodayPersistenceContract
                        .TodayEntry.COLUMN_NAME_TITLE));
                String url = cursor.getString(cursor.getColumnIndex(TodayPersistenceContract
                        .TodayEntry.COLUMN_NAME_URL));
                String auther = cursor.getString(cursor.getColumnIndex(TodayPersistenceContract
                        .TodayEntry.COLUMN_NAME_AUTHER));
                String imageurl = cursor.getString(cursor.getColumnIndex(TodayPersistenceContract
                        .TodayEntry.COLUMN_NAME_IMAGEURL));

                Today.Results.Item item = new Today.Results.Item();
                item.setDesc(desc);
                item.setUrl(url);
                item.setWho(auther);
                item.setImage(imageurl);

                if (type.equals("Android")) {
                    androids.add(item);
                } else if (type.equals("IOS")) {
                    ioss.add(item);
                } else if (type.equals("Video")) {
                    videos.add(item);
                } else if (type.equals("Web")) {
                    webs.add(item);
                } else if (type.equals("Resource")) {
                    resources.add(item);
                } else if (type.equals("Fuli")) {
                    fulis.add(item);
                } else if (type.endsWith("Xia")) {
                    xias.add(item);
                }
            }
        }
        results.setAndroids(androids);
        results.setIoss(ioss);
        results.setWebs(webs);
        results.setVideos(videos);
        results.setResources(resources);
        results.setFulis(fulis);
        results.setXias(xias);
        today.setResults(results);

        if (cursor != null) {
            cursor.close();
        }

        callback.onTodayLoaded(today);
    }

    @Override
    public void saveToday(String date, Today today) {
        String where = TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI.toString() + " = ?";
        mResolver.delete(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI, where, new
                String[]{date});
        ContentValues values = new ContentValues();
        List<Today.Results.Item> items = today.getResults().getAndroids();
        int size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Android");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }
        items = today.getResults().getIoss();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "IOS");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        items = today.getResults().getWebs();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Web");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        items = today.getResults().getVideos();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Video");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        items = today.getResults().getResources();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Resource");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        items = today.getResults().getFulis();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Fuli");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        items = today.getResults().getXias();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Xia");
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage());
            values.put(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt());
        }

        mResolver.insert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI, values);
    }
}
