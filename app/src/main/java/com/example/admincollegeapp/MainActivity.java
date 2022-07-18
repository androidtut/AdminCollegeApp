package com.example.admincollegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
CardView uploadNotice,uploadimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find view with id
        uploadNotice = findViewById(R.id.uploadnotice);
        uploadimage = findViewById(R.id.uploadimage);

//        set onclick listener to the view
        uploadNotice.setOnClickListener(this);
        uploadimage.setOnClickListener(this);

    }

    //set click listener with id
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.uploadnotice:
                startActivity(new Intent(MainActivity.this,UploadNotice.class));
                break;
            case R.id.uploadimage:
                startActivity(new Intent(MainActivity.this,UploadImageActivity.class));
                break;
        }
    }
}