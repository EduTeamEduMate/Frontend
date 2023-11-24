package com.example.edumate.views.activitites.mainScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import com.example.edumate.LoadingIImageGeneration;
import com.example.edumate.R;
import com.example.edumate.views.activitites.QuizInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateQuizFromImageActivity extends Activity {

    private ImageView backButton;
    private FrameLayout placeHolderImage;
    private Button generateButton;
    private EditText examTitleEditText;


    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;

    private String IMG_URL;

    private boolean doneUploading = false;

    private String extractedTextFromImage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_from_image);

        backButton = findViewById(R.id.backButton);
        placeHolderImage = findViewById(R.id.imageSelectContainer);
        generateButton = findViewById(R.id.generateButton);
        examTitleEditText = findViewById(R.id.examTitleEditText);

        selectedImageView = findViewById(R.id.selectedImageView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if (!title.isEmpty() ) {
                    if (doneUploading){
                        Intent generateExam = new Intent(GenerateQuizFromImageActivity.this, LoadingIImageGeneration.class);
                        generateExam.putExtra("EXTRACTED_TEXT", extractedTextFromImage);
                        generateExam.putExtra("EXAM_TITLE", title);
                        startActivity(generateExam);
                    }else{
                        Toast.makeText(GenerateQuizFromImageActivity.this, "Please upload an image", Toast.LENGTH_SHORT).show();
                    }
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
                generateButton.setText("Extracting Text From Image, Please Wait...");
                selectedImageView.setImageURI(selectedImageUri);
                uploadImageOnline(selectedImageUri);

            }
        }
    }


    private String uploadImageOnline(Uri selectedImageUri){
        String res = "";
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            System.out.println("Base64 Image String: " + encodedImage);

            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add("key", "6d207e02198a847aa98d0a2a901485a5")
                    .add("source", encodedImage)
                    .add("format", "json")
                    .build();

            Request request = new Request.Builder()
                    .url("https://freeimage.host/api/1/upload")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {


                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String jsonResponse = response.body().string();
                        System.out.println("JSON Response: " + jsonResponse);
                        getImageUrl(jsonResponse);
                        uploadImageUrlToOcrSpace(IMG_URL);
                    }
                }


            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    private void getImageUrl(String jsonResponse){
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONObject imageObject = jsonObject.getJSONObject("image");
            JSONObject mediumObject = imageObject.getJSONObject("medium");
            String desiredUrl = mediumObject.getString("url");

            System.out.println("Extracted URL: " + desiredUrl);
            IMG_URL = desiredUrl;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void uploadImageUrlToOcrSpace(String imageUrl) {
        OkHttpClient client = new OkHttpClient();


        RequestBody formBody = new FormBody.Builder()
                .add("apikey", "78df74f3b488957")
                .add("url", imageUrl)
                .add("language", "eng") // You can change the language as needed
                .add("isOverlayRequired", "true")
                .build();

        Request request = new Request.Builder()
                .url("https://api.ocr.space/parse/image")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();

                    try {
                        JSONObject jsonResponse = new JSONObject(responseString);
                        JSONArray parsedResults = jsonResponse.getJSONArray("ParsedResults");
                        if (parsedResults.length() > 0) {
                            JSONObject firstResult = parsedResults.getJSONObject(0);
                            String extractedText = firstResult.getString("ParsedText");

                            extractedTextFromImage = extractedText;
                            generateButton.setText("Done, Generate Exam Now!!");
                            doneUploading= true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Error: Failed to get a successful response");
                    System.out.println("Response Code: " + response.code());
                    System.out.println("Response Message: " + response.message());
                    if (response.body() != null) {
                        System.out.println("Error Body: " + response.body().string());
                    }
                }
            }
        });
    }

}

