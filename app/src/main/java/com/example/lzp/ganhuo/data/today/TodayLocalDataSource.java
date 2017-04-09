package com.example.lzp.ganhuo.data.today;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.example.lzp.ganhuo.app.BaseApplication;
import com.example.lzp.ganhuo.data.GHDbHelper;
import com.example.lzp.ganhuo.fragment.today.Today;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by SKJP on 2017/3/16.
 */

public class TodayLocalDataSource implements TodayDataSource {

    private static TodayLocalDataSource INSTANCE = null;

    private ContentResolver mResolver;
    private Map<String, Today> mCaches;

    private TodayLocalDataSource(Context context) {
        this.mResolver = context.getContentResolver();
        mCaches = new ConcurrentHashMap<>();
    }

    public static TodayLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TodayLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getToday(String date, LoadTodayCallback callback) {
        //从缓存中取数据
        if (mCaches.containsKey(date)) {
            Today today = mCaches.get(date);
            callback.onTodayLoaded(today);
            return;
        }
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
            cursor.moveToFirst();
            do {
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
                Today.Results.Item item;

                if (type.equals("Android")) {
                    item = new Today.Results.Android();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    androids.add(item);
                } else if (type.equals("IOS")) {
                    item = new Today.Results.Ios();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    ioss.add(item);
                } else if (type.equals("Video")) {
                    item = new Today.Results.Video();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    videos.add(item);
                } else if (type.equals("Web")) {
                    item = new Today.Results.Web();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    webs.add(item);
                } else if (type.equals("Resource")) {
                    item = new Today.Results.Resource();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    resources.add(item);
                } else if (type.equals("Fuli")) {
                    item = new Today.Results.Fuli();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    fulis.add(item);
                } else if (type.endsWith("Xia")) {
                    item = new Today.Results.Xia();
                    item.setDesc(desc);
                    item.setUrl(url);
                    item.setWho(auther);
                    item.setImage(imageurl);
                    xias.add(item);
                }
            } while (cursor.moveToNext());
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
        if ((androids.size() + ioss.size() + webs.size() + videos.size() + resources.size() + fulis.size() + xias.size()) == 0) {
            Log.e("Test", "empty");
            callback.onDataNotAvailable();
        } else {
            Log.e("Test", "not empty");
            callback.onTodayLoaded(today);
        }
    }

    @Override
    public void saveToday(String date, Today today) {
        mResolver.delete(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI, TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE + " = ?", new
                String[]{date});

        mCaches.put(date, today);//加入缓存

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        List<Today.Results.Item> items = today.getResults().getAndroids();
        int size = items.size();
        Log.e("Test", "TodayLocalDataSource saveToday size=" + size);
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Android").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }
        items = today.getResults().getIoss();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "IOS").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        items = today.getResults().getWebs();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Web").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        items = today.getResults().getVideos();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Video").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        items = today.getResults().getResources();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Resource").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        items = today.getResults().getFulis();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Fuli").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        items = today.getResults().getXias();
        size = items.size();
        for (int i = 0; i < size; i++) {
            Today.Results.Item item = items.get(i);
            operations.add(ContentProviderOperation.newInsert(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI).withValue
                    (TodayPersistenceContract.TodayEntry.COLUMN_NAME_TYPE, "Xia").
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_TITLE, item.getDesc()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_URL, item.getUrl()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_AUTHER, item.getWho()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL, item.getImage()).
                    withValue(TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE, item.getPublishedAt().replaceAll("-", "/")).withYieldAllowed(true).build());
        }

        try {
            mResolver.applyBatch(TodayPersistenceContract.CONTENT_AUTHORITY, operations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTodayImage(String date, LoadTodayImageCallback callback) {
        List<String> imageUrls = new ArrayList<>();
        //从缓存中取数据
        if (mCaches.containsKey(date)) {
            Today today = mCaches.get(date);
            imageUrls.addAll(getImageUrl(today.getResults().getAndroids()));
            imageUrls.addAll(getImageUrl(today.getResults().getIoss()));
            imageUrls.addAll(getImageUrl(today.getResults().getResources()));
            imageUrls.addAll(getImageUrl(today.getResults().getVideos()));
            imageUrls.addAll(getImageUrl(today.getResults().getWebs()));
            imageUrls.addAll(getImageUrl(today.getResults().getXias()));

        } else {
            String[] projection = {TodayPersistenceContract.TodayEntry.COLUMN_NAME_IMAGEURL};
            String selection = TodayPersistenceContract.TodayEntry.COLUMN_NAME_DATE + " = ?";
            String[] selectionArgs = {date};

            Cursor cursor = mResolver.query(TodayPersistenceContract.TodayEntry.CONTENT_TODAY_URI, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String url = cursor.getString(0);
                    if (!TextUtils.isEmpty(url)) {
                        imageUrls.add(url);
                    }
                }
                cursor.close();
            }
        }
        callback.onTodayImageLoaded(imageUrls);
    }

    private List<String> getImageUrl(List<Today.Results.Item> items) {
        List<String> results = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Today.Results.Item item : items) {
                results.add(item.getImage());
            }
        }
        return results;
    }
}
