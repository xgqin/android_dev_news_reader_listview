package com.glriverside.xgqin.listviewdemo;

import android.provider.BaseColumns;

public final class NewsContract {
    private NewsContract() {}

    public static class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_news";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "tbl_user";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PWD = "password";
    }
}
