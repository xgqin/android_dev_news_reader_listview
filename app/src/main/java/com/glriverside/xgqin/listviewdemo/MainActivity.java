package com.glriverside.xgqin.listviewdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String NEWS_ID = "news_id";
    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] titles = null;
    private String[] authors = null;
    private String[] contents = null;
    private TypedArray images;

    private List<News> newsList = new ArrayList<>();

    private NewsCursorAdapter cursorAdapter = null;

    private ListView lvNewsList;

    private FloatingActionButton fabRefresh = null;
    private SwipeRefreshLayout swipeRefresh = null;

    private static final int QUERY_LOADER_ID = 0;
    private static final int ADD_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNewsList = findViewById(R.id.lv_news_list);
        fabRefresh = findViewById(R.id.fab_refresh);
        swipeRefresh = findViewById(R.id.swipe_refresh);

        initData();

        cursorAdapter = new NewsCursorAdapter(MainActivity.this);

        cursorAdapter.swapCursor(null);
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

        getLoaderManager().initLoader(QUERY_LOADER_ID, null, this);

        fabRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fabRefresh.animate()
                        .rotation(-180)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fabRefresh.setRotation(0);
                    }
                });
                refreshData();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefresh.setRefreshing(false);
            }
        });

    }

    private void refreshData() {
        Random random = new Random();
        int index = random.nextInt(19);

        Bundle args = new Bundle();
        args.putString(NewsContract.NewsEntry.COLUMN_NAME_TITLE, titles[index]);
        args.putString(NewsContract.NewsEntry.COLUMN_NAME_AUTHOR, authors[index]);
        args.putString(NewsContract.NewsEntry.COLUMN_NAME_CONTENT, contents[index]);
        args.putString(NewsContract.NewsEntry.COLUMN_NAME_IMAGE, images.getString(index));

        getLoaderManager().restartLoader(ADD_LOADER_ID, args, this);
        getLoaderManager().restartLoader(QUERY_LOADER_ID, null, this);
    }

    private void initData() {
        int length;

        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);
        contents = getResources().getStringArray(R.array.contents);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case QUERY_LOADER_ID:
                return new NewsQueryAsyncCursorLoader(MainActivity.this);
            case ADD_LOADER_ID:
                return new NewsAddAsyncCursorLoader(MainActivity.this, args);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == QUERY_LOADER_ID) {
            cursorAdapter.swapCursor(cursor);
            cursorAdapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
