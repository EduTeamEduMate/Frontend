package com.example.edumate.views.activitites.mainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import com.example.edumate.R;
import com.example.edumate.views.activitites.QuizInfoActivity;

public class GenerateQuizFromImageActivity extends Activity {

    private ImageView backButton;
    private FrameLayout placeHolderImage;
    private Button generateButton;
    private EditText examTitleEditText;


    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_from_image); // Make sure the XML file is named "activity_upload_notes.xml"

        // Initialize Views
        backButton = findViewById(R.id.backButton);
        placeHolderImage = findViewById(R.id.imageSelectContainer);
        generateButton = findViewById(R.id.generateButton);
        examTitleEditText = findViewById(R.id.examTitleEditText);

        selectedImageView = findViewById(R.id.selectedImageView);

        // Set onClick listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the activity and returns to the previous one
            }
        });

        placeHolderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    openGallery();
                } else {
                    requestPermission();
                }
            }
        });


        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = examTitleEditText.getText().toString().trim();
                if (!title.isEmpty()) {
                    Intent generateExam = new Intent(GenerateQuizFromImageActivity.this, QuizInfoActivity.class);
                    startActivity(generateExam);
                } else {
                    Toast.makeText(GenerateQuizFromImageActivity.this, "Please enter a title for your quiz.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkPermission() {
        int result = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        openGallery();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access storage", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // Set the image URI of the ImageView to display the selected image
                selectedImageView.setImageURI(selectedImageUri);
                // Set the ImageView visibility to VISIBLE
                selectedImageView.setVisibility(View.VISIBLE);
            }
        }
    }

}

