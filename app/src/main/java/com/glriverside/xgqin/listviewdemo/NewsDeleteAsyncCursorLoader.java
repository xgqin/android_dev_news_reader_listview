package com.glriverside.xgqin.listviewdemo;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class NewsDeleteAsyncCursorLoader extends CursorLoader {

    private Context mContext;
    private final static String TAG = NewsDeleteAsyncCursorLoader.class.getSimpleName();

    private Bundle mArgs;
    private SQLiteDatabase mDb;

    public NewsDeleteAsyncCursorLoader(Context context, Bundle data, SQLiteDatabase db) {
        super(context);
        mContext = context;
        mArgs = data;
        mDb = db;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {

        Integer id = mArgs.getInt(NewsContract.NewsEntry._ID, -1);

        mDb.delete(
                NewsContract.NewsEntry.TABLE_NAME,
                NewsContract.NewsEntry._ID +" = ?",
                new String[]{Integer.toString(id)});

        return null;
    }
}
