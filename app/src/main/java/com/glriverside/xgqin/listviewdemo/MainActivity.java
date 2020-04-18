package com.glriverside.xgqin.listviewdemo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String NEWS_ID = "news_id";
    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] titles = null;
    private String[] authors = null;
    private TypedArray images;

    private List<News> newsList = new ArrayList<>();

    private NewsCursorAdapter cursorAdapter = null;
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

        cursorAdapter = new NewsCursorAdapter(MainActivity.this);

        cursorAdapter.swapCursor(cursor);
        lvNewsList.setAdapter(cursorAdapter);
        lvNewsList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvTitle = view.findViewById(R.id.tv_title);
                int newsId = Integer.parseInt(tvTitle.getTag().toString());

                Log.d(TAG, "newsId = " + newsId);
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra(NEWS_ID, newsId);

                startActivity(detailIntent);
            }
        });
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
