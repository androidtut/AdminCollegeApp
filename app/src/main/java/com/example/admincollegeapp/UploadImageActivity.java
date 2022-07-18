package com.example.admincollegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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
private StorageReference storageReference;
private DatabaseReference databaseReference;
String downloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        //find view with id
        categoryspinner = findViewById(R.id.categoryspinner);
        selectimage = findViewById(R.id.uploadimagetofirebase);
        uploadgallery = findViewById(R.id.useruploadimagetofirebase);
        uploadbtn = findViewById(R.id.uploadGallerybtn);

//        firebase databases reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        selectimage.setOnClickListener(v->{
            openGallery();
        });

        uploadbtn.setOnClickListener(v->{
            if(category != null){
                uploadImage();
            }
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

//   method to upload image to firebase storage
    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("gallery").child(finalimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            uploadgallerydata();
                        }
                    });
                }else{
                    Toast.makeText(UploadImageActivity.this, "image can't upload", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//end upload image method

//   method to upload data in firebase realtime database
    private void uploadgallerydata() {
        String uniquekey = databaseReference.push().getKey();
        databaseReference.child("gallery").child(category).child(uniquekey).setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UploadImageActivity.this, "successfully insert data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // method to open gallery and select image
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        uploadgallery.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}