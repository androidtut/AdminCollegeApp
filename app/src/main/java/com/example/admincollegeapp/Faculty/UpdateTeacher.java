package com.example.admincollegeapp.Faculty;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admincollegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacher extends AppCompatActivity {
TextInputEditText text1,text2,text3;
private Button updateteacherbtn,deleteteacher;
private DatabaseReference databaseReference;
String key,downloadurl = "";
Bitmap bitmap;
ImageView image1;
int SELECT_IMAGE =  1;
Intent intent;
private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        text1 = findViewById(R.id.teachernamewithid);
        text2 = findViewById(R.id.teacheremailwithid);
        text3 = findViewById(R.id.teacherpostwithid);
        updateteacherbtn = findViewById(R.id.updateteacherid);
        image1 = findViewById(R.id.teacherimgwithid);
        deleteteacher = findViewById(R.id.deleteteacherid);

        //get value
        intent = getIntent();
        String t1 = intent.getStringExtra("tnames");
        String t2 = intent.getStringExtra("temails");
        String t3 = intent.getStringExtra("tposts");
        String t4 = intent.getStringExtra("tcategorys");
        String t5 = intent.getStringExtra("timages");

        key = intent.getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher").child(t4.toString());
        storageReference = FirebaseStorage.getInstance().getReference();

//        setvalue in input
        text1.setText(t1);
        text2.setText(t2);
        text3.setText(t3);
        Glide.with(this).load(t5).into(image1);

        if(t5 != null){
            image1.setImageBitmap(bitmap);
        }else{
            image1.setImageResource(R.drawable.ic_launcher_background);
            Toast.makeText(UpdateTeacher.this, "No image selected", Toast.LENGTH_SHORT).show();
        }


//set click listener to the update button
        updateteacherbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
           }
        });

        // select image from gallery
        image1.setOnClickListener(v->{
                opengallery();
            });

// delete item firebase
        deleteteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertdialog();
            }
        });

//        c1ategory = findViewById(R.id.teachercategorywithid);
////        c1ategory = findViewById(R.id.teachercategorywithid);
//        teacherselectimg = findViewById(R.id.teacherimgwithid);
//        updateteacher = findViewById(R.id.updateteacherid);

//
////        c1ategory.setText("your name");
//
//        updateteacher.setOnClickListener(v->{
//            Toast.makeText(this, "update teacher", Toast.LENGTH_SHORT).show();
//        });
    }

    private void uploadimage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        StorageReference storageReference1;
        storageReference1 = storageReference.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadTask = storageReference1.putBytes(finalimg);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                          downloadurl = uri.toString();
                          Updatedata();
                        }
                    });
                }else{
                    Toast.makeText(UpdateTeacher.this, "something went problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void Updatedata(){
        String key = intent.getStringExtra("key");
        //to update data we can use HashMap and passing data same as firebase data
        HashMap d1 = new HashMap<>();
        d1.put("name",text1.getText().toString());
        d1.put("email",text2.getText().toString());
        d1.put("post",text3.getText().toString());
//                d1.put("category",t4);
        d1.put("image",downloadurl);

        databaseReference.child(key.toString()).updateChildren(d1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateTeacher.this, "successfully update", Toast.LENGTH_SHORT).show();
            }
        });

        int id = intent.getIntExtra("id",0);
        Toast.makeText(UpdateTeacher.this, "update"+id, Toast.LENGTH_SHORT).show();

    }

    //method to select image from gallery
    private void opengallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_IMAGE);;
    }

    //method to upload image in firebase


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showAlertdialog() {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Are you sure want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get key from TeacherAdapter
                        databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }
}