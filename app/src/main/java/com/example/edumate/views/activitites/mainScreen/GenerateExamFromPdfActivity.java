package com.example.edumate.views.activitites.mainScreen;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edumate.R;
import com.example.edumate.views.activitites.QuizInfoActivity;

public class GenerateExamFromPdfActivity extends Activity {

    private ImageView backButtonPDF;
    private FrameLayout placeHolderpdf;
    private Button generateButton;
    private EditText examTitleEditTextPdf;

    private ImageView pdfIcon;
    private TextView pdfFileName;

    private static final int PICK_PDF_FILE = 2;
    private static final int REQUEST_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_from_pdf);

        backButtonPDF = findViewById(R.id.backButtonPDF);
        placeHolderpdf = findViewById(R.id.pdfSelectContainer);
        generateButton = findViewById(R.id.generateButtonPdf);
        examTitleEditTextPdf = findViewById(R.id.examTitleEditTextPdf);

        pdfIcon = findViewById(R.id.pdfIcon);
        pdfFileName = findViewById(R.id.pdfFileName);

        backButtonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        placeHolderpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPdf();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                }

            }
        });


        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = examTitleEditTextPdf.getText().toString().trim();
                if (!title.isEmpty()) {
                    Intent generateExam = new Intent(GenerateExamFromPdfActivity.this, QuizInfoActivity.class);
                    startActivity(generateExam);
                } else {
                    Toast.makeText(GenerateExamFromPdfActivity.this, "Please enter a title for your quiz.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectPdf() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPdf();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PDF_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                pdfIcon.setVisibility(View.VISIBLE);
                pdfFileName.setVisibility(View.VISIBLE);
                pdfFileName.setText(uri.toString());
            }
        }
    }
}

