package com.example.vanur.booklistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

class BookLoader extends AsyncTaskLoader<List<Book>> {
    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUrl;



    public BookLoader(Context context, String keywordUrl) {
        super(context);
        mUrl = keywordUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
            if(mUrl == null){
                return null;
            }
            List<Book> books = QueryUtils.fetchBookData(mUrl);
            return  books;
    }
}
