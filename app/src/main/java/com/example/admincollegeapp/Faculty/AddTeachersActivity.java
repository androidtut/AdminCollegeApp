package com.example.admincollegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.example.admincollegeapp.R;
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
import java.net.URL;

public class AddTeachersActivity extends AppCompatActivity {
TextInputEditText tname,temail,tpost;
Spinner tcategory;
private String tename,teemail,tepost,category;
private MaterialButton addteacher;
private DatabaseReference databaseReference;
private StorageReference storageReference;
private ImageView teacherimg;
private final int SELECT_IMAGE =  1;
private Bitmap bitmap;
private String downloadurls = "";
private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teachers);
        tname = findViewById(R.id.teachername);
        temail = findViewById(R.id.teacheremail);
        tpost = findViewById(R.id.teacherpost);
        tcategory = findViewById(R.id.teachercategory);
        addteacher = findViewById(R.id.addteacher);
        teacherimg = findViewById(R.id.teacherimg);

//      create firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

//        select image after button click
        teacherimg.setOnClickListener(v->{
            OpenGallery();
        });

        //progress dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Insert to database");
        dialog.setMessage("wait while loading...");


       // set click listener to the button
        addteacher.setOnClickListener(v->{
            tename = tname.getText().toString();
            teemail = temail.getText().toString();
            tepost = tpost.getText().toString();
            if(tename.isEmpty()){
                tname.setError("Please Enter a teacher name");
            }else if(teemail.isEmpty()){
                tname.setError("Please Enter a teacher email");
            }else if(tepost.isEmpty()){
                tname.setError("Please Enter a teacher post");
            }else{
                if(bitmap != null){
                    InsertImage();
                }
               }
        });

//        create array
          String[] teachercategory = {"Select Category","c programming","c++ programming","computer","java programming"};

//        create arrayadapter to set category in spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddTeachersActivity.this, android.R.layout.simple_list_item_1,teachercategory);
        tcategory.setAdapter(adapter);

        tcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = tcategory.getSelectedItem().toString();
                Toast.makeText(AddTeachersActivity.this, ""+category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void InsertImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("teacher").child(finalimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dialog.dismiss();
                          downloadurls = uri.toString();
                          AddTeacherdata();
                        }
                    });
                }else{
                    dialog.dismiss();
                    Toast.makeText(AddTeachersActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //method to add teacher to the database
    private void AddTeacherdata() {
        dialog.show();
        String uniquekey = databaseReference.push().getKey();
        teacherdata teacherdata = new teacherdata(tename,teemail,tepost,category,uniquekey,downloadurls);
        databaseReference.child("teacher").child(category).child(uniquekey).setValue(teacherdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                Toast.makeText(AddTeachersActivity.this, "successfully added teacher", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(AddTeachersActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//end addteacherdata method


    //method to select image from gallery
    private void OpenGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if(requestCode == SELECT_IMAGE){
         if(resultCode == Activity.RESULT_OK){
             if(data != null){
                 try {
                     bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
     }

    }
}