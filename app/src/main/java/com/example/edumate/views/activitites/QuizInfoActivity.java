package com.example.edumate.views.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.edumate.R;
import com.example.edumate.views.activitites.quizActivities.QuizActivity;

public class QuizInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        int examId = getIntent().getIntExtra("EXAM_ID", -1);



        Button startQuizButton = findViewById(R.id.startButton);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizInfoActivity.this, LoadingActivity.class);
                intent.putExtra("EXAM_ID", examId);
                startActivity(intent);
            }
        });
    }
}