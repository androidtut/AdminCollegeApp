package com.example.admincollegeapp.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admincollegeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateFacultyActivity extends AppCompatActivity {
FloatingActionButton floatingActionButton;
RecyclerView cprogramming,c1programming,computer,javaprogramming;
DatabaseReference databaseReference;
private ArrayList<teacherdata>list1,list2,list3,list4,list5;
private TeacherAdapters tadaptes;
private  LinearLayoutManager linearLayoutManager;
LinearLayout computerNodata,cNodata,c1Nodata,javaNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        cprogramming = findViewById(R.id.cprogrammingrecycler);
        c1programming = findViewById(R.id.c1programmingrecycler);
        computer = findViewById(R.id.computerrecycler);
        javaprogramming = findViewById(R.id.javaprogrammingrecycler);
        computerNodata = findViewById(R.id.computerNodata);
        cNodata = findViewById(R.id.cprogrammingNodata);
        c1Nodata = findViewById(R.id.c1programmingNodata);
        javaNodata = findViewById(R.id.javaprogrammingNodata);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("teacher");

        floatingActionButton.setOnClickListener(v->{
            Intent intent = new Intent(UpdateFacultyActivity.this,AddTeachersActivity.class);
            startActivity(intent);
        });

        LoadJavaProgramming();
        LoadcProgramming();
        Loadc1Programming();
        Loadcomputer();

      }

  //method to load java programming teacher from database
    private void LoadJavaProgramming() {
        DatabaseReference databaseReference1 = databaseReference.child("java programming");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if(snapshot.exists()){ //it will check the data is present in database or not
                    javaprogramming.setVisibility(View.VISIBLE);
                    javaNodata.setVisibility(View.GONE);

                    //fetch all data from database
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        teacherdata td1 = snapshot1.getValue(teacherdata.class);
                        list1.add(td1);
                    }
                }else{
                    // set if data is not in database then No data layout include
                    javaprogramming.setVisibility(View.GONE);
                    javaNodata.setVisibility(View.VISIBLE);
                }
                tadaptes = new TeacherAdapters(list1,getApplicationContext());
                javaprogramming.setAdapter(tadaptes);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                javaprogramming.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //method to load c programming teacher from database
    private void LoadcProgramming() {
        DatabaseReference databaseReference2 = databaseReference.child("c programming");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if(snapshot.exists()){
                    cprogramming.setVisibility(View.VISIBLE);
                    cNodata.setVisibility(View.GONE);

                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        teacherdata td1 = snapshot1.getValue(teacherdata.class);
                        list2.add(td1);
                    }
                }else{
                    cprogramming.setVisibility(View.GONE);
                    cNodata.setVisibility(View.VISIBLE);
                }
                tadaptes = new TeacherAdapters(list2,getApplicationContext());
                cprogramming.setAdapter(tadaptes);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                cprogramming.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //method to load c++ programming teacher from database
    private void Loadc1Programming() {
        DatabaseReference databaseReference3 = databaseReference.child("c++ programming");

        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if(snapshot.exists()){
                    c1programming.setVisibility(View.VISIBLE);
                    c1Nodata.setVisibility(View.GONE);
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    teacherdata td1 = snapshot1.getValue(teacherdata.class);
                    list3.add(td1);
                }
                }else{
                    c1programming.setVisibility(View.GONE);
                    c1Nodata.setVisibility(View.VISIBLE);
                }
                tadaptes = new TeacherAdapters(list3,getApplicationContext());
                c1programming.setAdapter(tadaptes);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                c1programming.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //method to load computer teacher from database
    private void Loadcomputer() {
       DatabaseReference databaseReference4 = databaseReference.child("computer");

        databaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<>();
                if(snapshot.exists()) {
                    computerNodata.setVisibility(View.GONE);
                    computer.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        teacherdata td1 = snapshot1.getValue(teacherdata.class);
                        list4.add(td1);
                    }
                }else{
                    computerNodata.setVisibility(View.VISIBLE);
                    computer.setVisibility(View.GONE);
                }
                computer.setHasFixedSize(true);
                tadaptes = new TeacherAdapters(list4,getApplicationContext());
                computer.setAdapter(tadaptes);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                computer.setLayoutManager(linearLayoutManager);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
