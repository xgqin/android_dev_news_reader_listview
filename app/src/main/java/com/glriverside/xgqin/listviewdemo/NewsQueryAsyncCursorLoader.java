package com.glriverside.xgqin.listviewdemo;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NewsQueryAsyncCursorLoader extends CursorLoader {

    private Context mContext;
    private final static String TAG = NewsQueryAsyncCursorLoader.class.getSimpleName();

    public NewsQueryAsyncCursorLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        MyDbOpenHelper dbOpenHelper = new MyDbOpenHelper(mContext);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
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
