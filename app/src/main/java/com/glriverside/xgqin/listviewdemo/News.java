package com.glriverside.xgqin.listviewdemo;

import android.graphics.Bitmap;

public class News {
    private String mTitle;
    private String mAuthor;
    private String mContent;
    private int mImageId = -1;

    private Bitmap image;
    private String imageUri;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        this.mImageId = imageId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setImage(String image) {
        this.imageUri = image;
    }

    public String getImageUri() {
        return imageUri;
    }
}
