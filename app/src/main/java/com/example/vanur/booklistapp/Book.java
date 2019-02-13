package com.example.vanur.booklistapp;

import android.content.Intent;

public class Book {
    private String mBookName;
    private String mSubtitle;
    private String mAuthorName;
    private String mPublisher;
    private String mPages;
    private Intent mLink;

    public Book(String bookName, String subtitle, String authorName, String publisher, String pages, Intent link){
        mBookName = bookName;
        mSubtitle = subtitle;
        mAuthorName = authorName;
        mPublisher = publisher;
        mPages = pages;
        mLink = link;
    }

    public String getmBookName() {
        return mBookName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmPages() {
        return mPages;
    }

    public String getmPublisher() {
        return mPublisher;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public Intent getmLink() {
        return mLink;
    }
}
