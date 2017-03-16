package com.example.lzp.ganhuo.data.today;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.renderscript.Sampler;

import com.example.lzp.ganhuo.BuildConfig;

/**
 * Created by lzp on 2017/3/14.
 */

public class TodayPersistenceContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String CONTENT_TODAY_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + TodayEntry.TABLE_NAME;
    public static final String CONTENT_TODAY_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + TodayEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    public static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    private TodayPersistenceContract() {
    }

    public static Uri getBaseTodayUri(String todayId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_TODAY_ITEM_TYPE + SEPARATOR + todayId);
    }

    public static abstract class TodayEntry implements BaseColumns {
        public static final String TABLE_NAME = "today";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_AUTHER = "auther";
        public static final String COLUMN_NAME_IMAGEURL = "imageurl";
        public static final String COLUMN_NAME_DATE = "date";
        public static final Uri CONTENT_TODAY_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] TODAY_COLUMNS = new String[]{
                TodayEntry._ID,
                TodayEntry.COLUMN_NAME_TYPE,
                TodayEntry.COLUMN_NAME_TITLE,
                TodayEntry.COLUMN_NAME_URL,
                TodayEntry.COLUMN_NAME_AUTHER,
                TodayEntry.COLUMN_NAME_IMAGEURL,
                TodayEntry.COLUMN_NAME_DATE,
        };

        public static Uri buildTodayUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_TODAY_URI, id);
        }

        public static Uri buildTodayUriWith(String id) {
            Uri uri = CONTENT_TODAY_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildTodayUri() {
            return CONTENT_TODAY_URI.buildUpon().build();
        }
    }
}
