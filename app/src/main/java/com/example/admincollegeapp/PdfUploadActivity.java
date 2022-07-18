package com.example.admincollegeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class PdfUploadActivity extends AppCompatActivity {
private CardView uploadpdf;
private TextInputEditText pdftitle;
private MaterialButton uploadpdfbtn;
private String useruploadpdftitle;
private String displayName = null;
private final int SELECT_PDF = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_upload);

// find view with id
        uploadpdf = findViewById(R.id.uploadpdf);
        pdftitle = findViewById(R.id.pdftitle);
        uploadpdfbtn = findViewById(R.id.uploadpdfbtn);

        uploadpdf.setOnClickListener(v->{
            useruploadpdftitle = pdftitle.getText().toString();
            Selectpdf();
        });
    }

 // select pdf from gallery
    private void Selectpdf() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),SELECT_PDF);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PDF){
            if(resultCode == Activity.RESULT_OK){
               Uri pdfdata = data.getData();
                Toast.makeText(this, ""+pdfdata, Toast.LENGTH_SHORT).show();
            }
        }
    }
}