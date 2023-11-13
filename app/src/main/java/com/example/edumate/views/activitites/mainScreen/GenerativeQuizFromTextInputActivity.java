package com.example.edumate.views.activitites.mainScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.edumate.R;
import com.example.edumate.views.activitites.QuizInfoActivity;

public class GenerativeQuizFromTextInputActivity extends Activity {

    private ImageView backButtonTextInput;
    private EditText additionalTextInput;
    private Button generateButtonTextInput;
    private EditText examTitleEditTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_from_text_input); // Make sure the XML file is named "activity_upload_notes.xml"

        // Initialize Views
        backButtonTextInput = findViewById(R.id.backButtonTextInput);
        generateButtonTextInput = findViewById(R.id.generateButtonTextInput);
        examTitleEditTextInput = findViewById(R.id.examTitleEditTextInput);
        additionalTextInput = findViewById(R.id.additionalTextInput);
        // Set onClick listeners
        backButtonTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the activity and returns to the previous one
            }
        });



        generateButtonTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = examTitleEditTextInput.getText().toString().trim();
                String textInput = additionalTextInput.getText().toString().trim();
                if (title.isEmpty() || textInput.isEmpty()) {
                    Toast.makeText(GenerativeQuizFromTextInputActivity.this, "Please enter a title for your quiz.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent generateExam = new Intent(GenerativeQuizFromTextInputActivity.this, QuizInfoActivity.class);
                    startActivity(generateExam);
                }
            }
        });
    }
}

