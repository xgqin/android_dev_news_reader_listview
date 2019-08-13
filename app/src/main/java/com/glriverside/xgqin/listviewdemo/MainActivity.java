package com.glriverside.xgqin.listviewdemo;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_TITLE = "news_title";
    private static final String NEWS_AUTHOR = "news_author";

    private String[] titles = null;
    private String[] authors = null;

    private List<Map<String, String>> dataList = new ArrayList<>();
    private ListView lvNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNewsList = findViewById(R.id.lv_news_list);

        initData();

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                MainActivity.this,
                dataList, android.R.layout.simple_list_item_2,
                new String[]{NEWS_TITLE, NEWS_AUTHOR},
                new int[]{android.R.id.text1, android.R.id.text2});
        lvNewsList.setAdapter(simpleAdapter);
    }

    private void initData() {
        int length;

        titles = getResources().getStringArray(R.array.titles);
        authors = getResources().getStringArray(R.array.authors);

        if (titles.length > authors.length) {
            length = authors.length;
        } else {
            length = titles.length;
        }

        for (int i = 0; i < length; i++) {
            Map map = new HashMap();
            map.put(NEWS_TITLE, titles[i]);
            map.put(NEWS_AUTHOR, authors[i]);

            dataList.add(map);
        }
    }
}
