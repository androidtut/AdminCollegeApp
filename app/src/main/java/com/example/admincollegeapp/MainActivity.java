package com.example.admincollegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admincollegeapp.Faculty.UpdateFacultyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
CardView uploadNotice,uploadimage,uploadebook,uploadfaculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find view with id
        uploadNotice = findViewById(R.id.uploadnotice);
        uploadimage = findViewById(R.id.uploadimage);
        uploadebook = findViewById(R.id.uploadebook);
        uploadfaculty = findViewById(R.id.uploadfaculty);

//        set onclick listener to the view
        uploadNotice.setOnClickListener(this);
        uploadimage.setOnClickListener(this);
        uploadebook.setOnClickListener(this);
        uploadfaculty.setOnClickListener(this);

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
            case R.id.uploadebook:
                startActivity(new Intent(MainActivity.this,PdfUploadActivity.class));
                break;
            case R.id.uploadfaculty:
                startActivity(new Intent(MainActivity.this, UpdateFacultyActivity.class));
                break;
        }
    }
}