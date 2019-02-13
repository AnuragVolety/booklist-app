package com.example.vanur.booklistapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{


    private BookAdapter mAdapter;
    private String KEYWORD_URL ="https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_LOADER_ID = 1;
    public static final String LOG_TAG = BookActivity.class.getName();
    private TextView mEmptytTextView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        String search = intent.getStringExtra("SearchResult");
        KEYWORD_URL = KEYWORD_URL + search + "&maxResults=40";

        Log.e(LOG_TAG,"Anurag KEYWORD_URL is" + KEYWORD_URL);

        TextView showing = findViewById(R.id.showing);
        showing.setText("Showing stuff related to \""+search+"\"");

        ListView bookListView = findViewById(R.id.list);
        mEmptytTextView = findViewById(R.id.empty);
        bookListView.setEmptyView(mEmptytTextView);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        }
        else{
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptytTextView.setText("No Internet Connection");
        }

        mAdapter= new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = mAdapter.getItem(position);
                Intent temp = book.getmLink();
                startActivity(temp);
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        return (new BookLoader(this,KEYWORD_URL));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        mEmptytTextView.setText("Oh dear, it's empty down here!!");
        mAdapter.clear();
        if(data != null && !data.isEmpty()){
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
