package com.glriverside.xgqin.listviewdemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    int newsId = -1;

    private MyDbOpenHelper myDbOpenHelper = null;
    private SQLiteDatabase db = null;
    private Cursor cursor = null;

    private String selection = NewsContract.NewsEntry._ID + " = ?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.article_title);
        TextView tvAuthor = findViewById(R.id.article_subtitle);
        TextView tvContent = findViewById(R.id.article_text);

        Intent intent = getIntent();

        newsId = intent.getIntExtra(MainActivity.NEWS_ID, -1);

        if (newsId != -1) {
            myDbOpenHelper = new MyDbOpenHelper(DetailActivity.this);
            db = myDbOpenHelper.getReadableDatabase();

            cursor = db.query(NewsContract.NewsEntry.TABLE_NAME,
                    null,
                    selection,
                    new String[]{Integer.toString(newsId)},
                    null,
                    null,
                    null);

            if (cursor.getCount() == 1) {
                int titleIndex = cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_TITLE);
                int authorIndex = cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_AUTHOR);
                int imageIndex = cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_IMAGE);
                int contentIndex = cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_CONTENT);

                cursor.moveToFirst();

                String title = cursor.getString(titleIndex);
                String author = cursor.getString(authorIndex);
                String image = cursor.getString(imageIndex);
                String content = cursor.getString(contentIndex);

                Bitmap bitmap = BitmapFactory.decodeStream(
                        getClass().getResourceAsStream("/" + image));

                tvTitle.setText(title);
                tvAuthor.setText(author);
                tvContent.setText(content);
            }
        }
    }
}
