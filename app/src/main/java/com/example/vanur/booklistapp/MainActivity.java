package com.example.vanur.booklistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText search = findViewById(R.id.search);
        Button imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchResult = search.getText().toString();
                if(searchResult.equals("")){
                    Toast.makeText(MainActivity.this,"Fill in the topic of your interest.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                Intent intent = new Intent(MainActivity.this,BookActivity.class);
                intent.putExtra("SearchResult", searchResult);
                startActivity(intent);
                }
            }
        });
    }
}
