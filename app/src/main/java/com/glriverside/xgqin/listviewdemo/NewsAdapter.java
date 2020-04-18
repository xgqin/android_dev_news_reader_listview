package com.glriverside.xgqin.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    private List<News> mNewsData;
    private Context mContext;
    private int resourceId;

    public NewsAdapter(Context context, int resourceId, List<News> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        View view ;

        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvTitle  = view.findViewById(R.id.tv_title);
            viewHolder.tvAuthor = view.findViewById(R.id.tv_subtitle);
            viewHolder.ivImage = view.findViewById(R.id.iv_image);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvTitle.setText(news.getTitle());
        viewHolder.tvAuthor.setText(news.getAuthor());

        if (news.getImageId() != -1) {
            viewHolder.ivImage.setImageResource(news.getImageId());
        } else {
            viewHolder.ivImage.setImageBitmap(news.getImage());
        }

        return view;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
    }
}
