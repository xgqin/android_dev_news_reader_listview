package com.glriverside.xgqin.listviewdemo;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] titles = null;
    private String[] authors = null;
    private TypedArray images;

    private List<News> newsList = new ArrayList<>();

    private NewsAdapter newsAdapter = null;

    private MyDbOpenHelper myDbOpenHelper = null;
    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    private ListView lvNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNewsList = findViewById(R.id.lv_news_list);

        // initData();

        myDbOpenHelper = new MyDbOpenHelper(MainActivity.this);
        db = myDbOpenHelper.getReadableDatabase();

        cursor = db.query(NewsContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
                );

        int titleIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.COLUMN_NAME_TITLE);
        int authorIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.COLUMN_NAME_AUTHOR);
        int imageIndex = cursor.getColumnIndex(
                NewsContract.NewsEntry.COLUMN_NAME_IMAGE);

        while (cursor.moveToNext()) {
            News news = new News();
            String title = cursor.getString(titleIndex);
            String author = cursor.getString(authorIndex);
            String image = cursor.getString(imageIndex);

            Bitmap bitmap = BitmapFactory.decodeStream(
                    getClass().getResourceAsStream("/" + image));

            news.setTitle(title);
            news.setAuthor(author);
            news.setImage(bitmap);
            newsList.add(news);
        }

        newsAdapter = new NewsAdapter(
                MainActivity.this,
                R.layout.list_item,
                newsList
        );

        lvNewsList.setAdapter(newsAdapter);
    }

    private void initData() {
        int length;

        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        images = getResources().obtainTypedArray(R.array.images);

        if (titles.length > authors.length) {
            length = authors.length;
        } else {
            length = titles.length;
        }

        for (int i = 0; i < length; i++) {
            News news = new News();
            news.setTitle(titles[i]);
            news.setAuthor(authors[i]);
            news.setImageId(images.getResourceId(i, 0));

            newsList.add(news);
        }
    }
}
