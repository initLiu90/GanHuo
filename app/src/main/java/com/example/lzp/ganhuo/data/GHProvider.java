package com.example.lzp.ganhuo.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;

import com.example.lzp.ganhuo.BuildConfig;
import com.example.lzp.ganhuo.data.today.TodayPersistenceContract;
import com.example.lzp.ganhuo.fragment.today.Today;

/**
 * Created by lzp on 2017/3/14.
 */

public class GHProvider extends ContentProvider {
    private static final int TODAY = 100;
    private static final int TODAY_ITEM = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private GHDbHelper mGHDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = BuildConfig.APPLICATION_ID;

        matcher.addURI(authority, "today", TODAY);
        matcher.addURI(authority, "today/*", TODAY_ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mGHDbHelper = new GHDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TODAY:
                return "vnd.android.cursor.dir/" + BuildConfig.APPLICATION_ID + "/" + "today";
            case TODAY_ITEM:
                return "vnd.android.cursor.item/" + BuildConfig.APPLICATION_ID + "/" + "today";
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case TODAY:
                Log.e("Test", "query :" + selection + selectionArgs[0]);
                retCursor = mGHDbHelper.getReadableDatabase().query(TodayPersistenceContract.TodayEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TODAY_ITEM:
                String[] where = {uri.getLastPathSegment()};
                retCursor = mGHDbHelper.getReadableDatabase().query(TodayPersistenceContract.TodayEntry.TABLE_NAME,
                        projection,
                        TodayPersistenceContract.TodayEntry._ID + " = ?",
                        where,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("unknow uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mGHDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TODAY:
                long _id = db.insert(TodayPersistenceContract.TodayEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = TodayPersistenceContract.TodayEntry.buildTodayUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mGHDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case TODAY:
                rowsDeleted = db.delete(TodayPersistenceContract.TodayEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mGHDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case TODAY:
                rowsUpdated = db.update(TodayPersistenceContract.TodayEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
