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

    private OnItemDeletedListener listener = null;

    private static final int VIEW_TYPE_ONE = 0;
    private static final int VIEW_TYPE_TWO = 1;
    private static final int VIEW_TYPE_COUNT = 2;

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return VIEW_TYPE_TWO;
        }

        return VIEW_TYPE_ONE;
    }

    public interface OnItemDeletedListener {
        public void onDeleted(Integer id);
    }

    public NewsCursorAdapter(Context context) {
        super(context, null, 0);
        mContext = context;

        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemDeletedListener(NewsCursorAdapter.OnItemDeletedListener listener) {
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int position = cursor.getPosition();

        final View itemView;

        if (getItemViewType(position) == VIEW_TYPE_TWO) {
            itemView =
                    mInflater.inflate(
                            R.layout.list_item,
                            viewGroup,
                            false
                    );
        }
        else {
            itemView =
                    mInflater.inflate(
                            R.layout.list_item_type_one,
                            viewGroup,
                            false
                    );
        }

        final  ViewHolder holder = new ViewHolder();
        holder.tvTitle = itemView.findViewById(R.id.tv_title);
        holder.tvAuthor = itemView.findViewById(R.id.tv_subtitle);
        holder.ivImage = itemView.findViewById(R.id.iv_image);
        holder.ivImage2 = itemView.findViewById(R.id.iv_image2);
        holder.ivImage3 = itemView.findViewById(R.id.iv_image3);
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

        if (holder.ivImage2 != null) {
            holder.ivImage2.setImageBitmap(bitmap);
        }

        if (holder.ivImage3 != null) {
            holder.ivImage3.setImageBitmap(bitmap);
        }

        holder.tvTitle.setTag(newsId);

        holder.ivDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(holder.tvTitle.getTag().toString());

                if (listener != null) {
                    listener.onDeleted(id);
                }
            }
        });
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
        ImageView ivImage2;
        ImageView ivImage3;
        ImageView ivDelete;
    }
}