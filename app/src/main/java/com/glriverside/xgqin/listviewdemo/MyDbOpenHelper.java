package com.glriverside.xgqin.listviewdemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbOpenHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
                    NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY, " +
                    NewsContract.NewsEntry.COLUMN_NAME_TITLE + " VARCHAR(200), " +
                    NewsContract.NewsEntry.COLUMN_NAME_AUTHOR + " VARCHAR(100), " +
                    NewsContract.NewsEntry.COLUMN_NAME_CONTENT + " TEXT, " +
                    NewsContract.NewsEntry.COLUMN_NAME_IMAGE + " VARCHAR(100) " +
                    ");" +
            "CREATE TABLE " + NewsContract.UserEntry.TABLE_NAME + " (" +
                    NewsContract.UserEntry._ID + " INTEGER PRIMARY KEY, " +
                    NewsContract.UserEntry.COLUMN_NAME_NAME + " VARCHAR(200), " +
                    NewsContract.UserEntry.COLUMN_NAME_PWD + " VARCHAR(100), " +
                    ");"
            ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "news.db";

    private Context mContext;

    private static final String TAG = MyDbOpenHelper.class.getSimpleName();

    public MyDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        Log.d(TAG, "Creating table : " + NewsContract.NewsEntry.TABLE_NAME);
        Log.d(TAG, "With sql script : " + SQL_CREATE_ENTRIES);

        initDb(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    private void initDb(SQLiteDatabase sqLiteDatabase) {
        Resources resources = mContext.getResources();
        String[] titles = resources.getStringArray(R.array.titles);
        String[] authors = resources.getStringArray(R.array.authors);
        String[] contents = resources.getStringArray(R.array.contents);
        TypedArray images = resources.obtainTypedArray(R.array.images);

        int length = 0;
        length = Math.min(titles.length, authors.length);
        length = Math.min(length, contents.length);
        length = Math.min(length, images.length());

        for (int i = 0; i < length; i++) {
            ContentValues values = new ContentValues();
            values.put(NewsContract.NewsEntry.COLUMN_NAME_TITLE, titles[i]);
            values.put(NewsContract.NewsEntry.COLUMN_NAME_AUTHOR, authors[i]);
            values.put(NewsContract.NewsEntry.COLUMN_NAME_CONTENT, contents[i]);
            values.put(NewsContract.NewsEntry.COLUMN_NAME_IMAGE, images.getString(i));

            long r = sqLiteDatabase.insert(NewsContract.NewsEntry.TABLE_NAME, null, values);
            Log.d(TAG, "Insert new entry into table " + NewsContract.NewsEntry.TABLE_NAME +
                    " with result : " + Long.toString(r));
            values.clear();
        }
    }
}
