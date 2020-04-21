package com.glriverside.xgqin.listviewdemo;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsQueryAsyncCursorLoader extends CursorLoader {

    private Context mContext;
    private final static String TAG = NewsQueryAsyncCursorLoader.class.getSimpleName();

    private SQLiteDatabase db;

    public NewsQueryAsyncCursorLoader(Context context, SQLiteDatabase db) {
        super(context);
        mContext = context;
        this.db = db;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = db.query(
                NewsContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NewsContract.NewsEntry._ID +" DESC");

        return cursor;
    }
}
