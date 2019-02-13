package com.example.vanur.booklistapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(BookActivity context, List<Book> books) {
        super(context, 0, books);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Book currentBook = getItem(position);
        TextView bookName = listItemView.findViewById(R.id.book);
        bookName.setText(currentBook.getmBookName());

        TextView subTitle = listItemView.findViewById(R.id.subtitle);
        subTitle.setVisibility(View.VISIBLE);
        if(currentBook.getmSubtitle().equals(""))
        {
            subTitle.setVisibility(View.GONE);
        }
        else
        {
            subTitle.setText(currentBook.getmSubtitle());
        }

        TextView authorName = listItemView.findViewById(R.id.author);
        authorName.setText(currentBook.getmAuthorName());

        TextView publisherName = listItemView.findViewById(R.id.publisher);
        publisherName.setText(currentBook.getmPublisher());


        TextView pages = listItemView.findViewById(R.id.pages);
        if(Integer.parseInt(currentBook.getmPages())==0){
            pages.setText("");
        }
        else {
            pages.setText(currentBook.getmPages()+" pages");
        }

        return listItemView;
    }
}
