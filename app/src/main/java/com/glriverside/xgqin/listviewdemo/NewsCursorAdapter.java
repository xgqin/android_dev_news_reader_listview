package com.glriverside.xgqin.listviewdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsCursorAdapter extends CursorAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private MyDbOpenHelper myDbOpenHelper = null;
    private SQLiteDatabase db = null;

    public NewsCursorAdapter(Context context) {
        super(context, null, 0);
        mContext = context;

        mInflater = LayoutInflater.from(context);

        myDbOpenHelper = new MyDbOpenHelper(mContext);
        db = myDbOpenHelper.getReadableDatabase();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        final View itemView =
                mInflater.inflate(
                        R.layout.list_item,
                        viewGroup,
                        false
                );

        final  ViewHolder holder = new ViewHolder();
        holder.tvTitle = itemView.findViewById(R.id.tv_title);
        holder.tvAuthor = itemView.findViewById(R.id.tv_subtitle);
        holder.ivImage = itemView.findViewById(R.id.iv_image);
        holder.ivDelete = itemView.findViewById(R.id.iv_delete);

        itemView.setTag(holder);

        return itemView;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();

        final String title = cursor.getString(
                cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_TITLE));

        final String author = cursor.getString(
                cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_AUTHOR));

        final String imageResource = cursor.getString(
                cursor.getColumnIndex(
                        NewsContract.NewsEntry.COLUMN_NAME_IMAGE));

        final int newsId = cursor.getInt(
                cursor.getColumnIndex(
                        NewsContract.NewsEntry._ID));

        holder.tvTitle.setText(title);
        holder.tvAuthor.setText(author);

        Bitmap bitmap = BitmapFactory.decodeStream(getClass().getResourceAsStream("/" + imageResource));
        holder.ivImage.setImageBitmap(bitmap);

        holder.tvTitle.setTag(newsId);

        holder.ivDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(holder.tvTitle.getTag().toString());
                db.delete(NewsContract.NewsEntry.TABLE_NAME,
                        NewsContract.NewsEntry._ID + " = ?",
                        new String[]{Integer.toString(id)});
                Cursor newCursor = db.query(NewsContract.NewsEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        NewsContract.NewsEntry._ID + " DESC");

                swapCursor(newCursor);
                notifyDataSetChanged();
            }
        });
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
        ImageView ivDelete;
    }
}
