package com.example.admincollegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText notice;
    MaterialButton uploadnotice;
    ImageView uploadimages, useruploadimages;
    String userenternotice;
    final int SELECT_IMAGE = 1;
    Bitmap bitmap;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String uniquekey, date, time;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String downloadUrl = "";
    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

//      find view with id
        notice = findViewById(R.id.userenternotice);
        uploadnotice = findViewById(R.id.uploadNotices);
        uploadimages = findViewById(R.id.uploadimages);
        useruploadimages = findViewById(R.id.useruploadimages);

        //set click listener to the image
        uploadimages.setOnClickListener(this);

//         create firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        calendar = Calendar.getInstance();

//      Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading to database");
        progressDialog.setMessage("Wait while Loading...");

//      set onclick listener
        uploadnotice.setOnClickListener(v -> {
            //get text from input and convert to string
            userenternotice = notice.getText().toString();
            if (userenternotice.isEmpty()) {
                notice.setError("Please Enter a notice");
                notice.requestFocus();
            } else if (bitmap == null) {
                uploaddata();
            } else {
                uploadimages();
            }
        });//end upload notice button click listener

    }

    //  method to upload images in firebase storage
    private void uploadimages() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] fileimg = baos.toByteArray();
        StorageReference filepath;
        filepath = storageReference.child("Notice").child(fileimg+"jpg");
        UploadTask uploadTask = filepath.putBytes(fileimg);

        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = String.valueOf(uri);
                            uploaddata();
                        }
                    });
                }else{
                    Toast.makeText(UploadNotice.this, "can't upload image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //end upload images

    // method to upload data in firebase real time database
    private void uploaddata() {
        progressDialog.show();
        DatabaseReference d1 = databaseReference.child("Notice");
//      generate unique key
        final String uniquekeys = d1.push().getKey();

//      get date using calendar
        simpleDateFormat = new SimpleDateFormat("yy:MM:dd");
        date = simpleDateFormat.format(calendar.getTime());

//      get time using calendar
        simpleDateFormat = new SimpleDateFormat("hh:ss: a");
        time = simpleDateFormat.format(calendar.getTime());

        Notice notice = new Notice(userenternotice,downloadUrl,date,time,uniquekeys);
        d1.child(uniquekeys).setValue(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(UploadNotice.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadNotice.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //end upload data


    //  set click listener with view using switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uploadimages:
                openGallery();
                break;
        }
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
                        useruploadimages.setImageBitmap(bitmap);
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