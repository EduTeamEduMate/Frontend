package com.example.edumate.views.activitites.quizActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.edumate.R;

import java.util.List;


import com.example.edumate.views.activitites.QuizInfoActivity;
import com.example.edumate.views.activitites.mainScreen.MainScreenActivity;
import com.example.edumate.views.adapters.ResultAdapter;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private Button btnTryAgain;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        recyclerView = findViewById(R.id.rvQuizResults);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<QuizResult> quizResults = getQuizResults();

        resultAdapter = new ResultAdapter(quizResults);
        recyclerView.setAdapter(resultAdapter);

        btnTryAgain = findViewById(R.id.btnTryAgain);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackToQuizStart();
            }
        });
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackToHome();
            }
        });
    }

    private void navigateBackToQuizStart() {
        Intent intent = new Intent(this, QuizInfoActivity.class);
        startActivity(intent);
        finish();
    }
    private void navigateBackToHome() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }



    private List<QuizResult> getQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        ArrayList<String> selectedAnswers = getIntent().getStringArrayListExtra("selectedAnswers");
        ArrayList<String> correctAnswers = getIntent().getStringArrayListExtra("correctAnswers");

        for (int i = 0; i < selectedAnswers.size(); i++) {
            String userAnswer = selectedAnswers.get(i);
            String correctAnswer = correctAnswers.get(i);
            results.add(new QuizResult("Question " + (i + 1), userAnswer, correctAnswer));
        }

        return results;
    }

}

