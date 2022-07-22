package com.example.admincollegeapp.Faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admincollegeapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class TeacherAdapters extends RecyclerView.Adapter<TeacherAdapters.TeacherViewHolder>{
    ArrayList<teacherdata>td;
    Context context;

    public TeacherAdapters(ArrayList<teacherdata> td, Context context) {
        this.td = td;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_items_layout,parent,false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        final teacherdata tmodels = td.get(position);
        holder.ftname.setText(tmodels.getName());
        holder.ftemail.setText(tmodels.getEmail());
        holder.ftpost.setText(tmodels.getPost());
        holder.ftname.setText(tmodels.getName());
        holder.ftcategory.setText(tmodels.getCategory());

        Glide.with(context).load(tmodels.getImage()).into(holder.profileimg);

        //set onclick listerner in recyclerview adapter
        holder.ftupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(),UpdateTeacher.class);
                //if you have problem in set onclick listener in adapter then set //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tnames", tmodels.getName());
                intent.putExtra("temails", tmodels.getEmail());
                intent.putExtra("tposts", tmodels.getPost());
                intent.putExtra("tcategorys",tmodels.getCategory());
                intent.putExtra("timages",tmodels.getImage());
                intent.putExtra("key",tmodels.getKey());
                intent.putExtra("id",position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return td.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
      TextView ftname,ftemail,ftpost,ftcategory;
      Button ftupdate;
      ImageView profileimg;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            ftname = itemView.findViewById(R.id.facultyteachername);
            ftemail = itemView.findViewById(R.id.facultyteacheremail);
            ftpost = itemView.findViewById(R.id.facultyteacherpost);
            ftupdate = itemView.findViewById(R.id.updateinfo);
            profileimg = itemView.findViewById(R.id.teacherprofile);
            ftcategory = itemView.findViewById(R.id.facultyteachercategory);
        }
    }
}
