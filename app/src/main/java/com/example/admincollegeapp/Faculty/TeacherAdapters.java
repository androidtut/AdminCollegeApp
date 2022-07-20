package com.example.admincollegeapp.Faculty;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
            ftupdate = itemView.findViewById(R.id.uploadfaculty);
            profileimg = itemView.findViewById(R.id.teacherprofile);
            ftcategory = itemView.findViewById(R.id.facultyteachercategory);
        }
    }
}
