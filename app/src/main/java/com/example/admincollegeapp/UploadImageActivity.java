package com.example.admincollegeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class UploadImageActivity extends AppCompatActivity {
private Spinner categoryspinner;
private CardView selectimage;
private MaterialButton uploadbtn;
private ImageView uploadgallery;
private final int SELECT_IMAGE = 1;
private String category;
private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        //find view with id
        categoryspinner = findViewById(R.id.categoryspinner);
        selectimage = findViewById(R.id.uploadimagetofirebase);
        uploadgallery = findViewById(R.id.useruploadimagetofirebase);
        uploadbtn = findViewById(R.id.uploadGallerybtn);


        selectimage.setOnClickListener(v->{
            openGallery();
        });

        //create array
        String[]data = {"Select category","Convocation","Independence Day","Others Events"};

        //create arrayadapter to show data from spinner
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(UploadImageActivity.this, android.R.layout.simple_list_item_1,data);
        categoryspinner.setAdapter(adapter);

       // select item from category
        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = categoryspinner.getSelectedItem().toString();
                Toast.makeText(UploadImageActivity.this, "you select "+category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

// select image from gallery
    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == SELECT_IMAGE){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                        uploadgallery.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}