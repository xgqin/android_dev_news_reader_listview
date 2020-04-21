package com.glriverside.xgqin.listviewdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class NewsAddAsyncCursorLoader extends CursorLoader {

    private Context mContext;
    private Bundle args;
    private SQLiteDatabase db;

    private final static String TAG = NewsAddAsyncCursorLoader.class.getSimpleName();

    public NewsAddAsyncCursorLoader(Context context, Bundle args, SQLiteDatabase db) {
        super(context);
        mContext = context;
        this.args = args;
        this.db = db;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        String title = args.getString(NewsContract.NewsEntry.COLUMN_NAME_TITLE);
        String author = args.getString(NewsContract.NewsEntry.COLUMN_NAME_AUTHOR);
        String content = args.getString(NewsContract.NewsEntry.COLUMN_NAME_CONTENT);
        String image = args.getString(NewsContract.NewsEntry.COLUMN_NAME_IMAGE);

        ContentValues contentValues = new ContentValues();
        contentValues.put(NewsContract.NewsEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(NewsContract.NewsEntry.COLUMN_NAME_AUTHOR, author);
        contentValues.put(NewsContract.NewsEntry.COLUMN_NAME_CONTENT, content);
        contentValues.put(NewsContract.NewsEntry.COLUMN_NAME_IMAGE, image);

        db.insert(NewsContract.NewsEntry.TABLE_NAME, null, contentValues);

        return null;
    }
}
