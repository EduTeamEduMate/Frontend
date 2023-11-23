package com.example.edumate.views.activitites.quizActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView scoreTextView;

    private TextView feedbackTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);


        recyclerView = findViewById(R.id.rvQuizResults);
        scoreTextView = findViewById(R.id.scoreTextView); // Assuming you have a TextView with this ID
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        feedbackTextView = findViewById(R.id.feedbackTextView);
        List<QuizResult> quizResults = getQuizResults();

        resultAdapter = new ResultAdapter(quizResults);
        recyclerView.setAdapter(resultAdapter);

        btnBackToHome = findViewById(R.id.btnBackToHome);

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBackToHome();
            }
        });
    }

    private void navigateBackToHome() {
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateFeedbackMessage(int score) {
        String feedback;
        if (score >= 8) {

            if (score==10){
                View sep = findViewById(R.id.sep);
                sep.setVisibility(View.INVISIBLE);

                TextView header = findViewById(R.id.tvWrongAnswersHeader);
                header.setVisibility(View.INVISIBLE);
            }


            feedback = "Excellent! \uD83D\uDE0A"; // Emoji for a smiling face
        } else if (score >= 7) {
            feedback = "Very Good! \uD83D\uDE42"; // Emoji for a slightly smiling face
        } else if (score >= 5) {
            feedback = "Good \uD83D\uDE10"; // Emoji for a neutral face
        } else {
            feedback = "Needs Improvement \uD83D\uDE41"; // Emoji for a frowning face
        }
        feedbackTextView.setText(feedback);
    }

    private List<QuizResult> getQuizResults() {
        List<QuizResult> results = new ArrayList<>();
        ArrayList<String> selectedAnswers = getIntent().getStringArrayListExtra("selectedAnswers");
        ArrayList<String> correctAnswers = getIntent().getStringArrayListExtra("correctAnswers");

        int score = 0;
        for (int i = 0; i < selectedAnswers.size(); i++) {
            String userAnswer = selectedAnswers.get(i);
            String correctAnswer = correctAnswers.get(i);
            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
                score++; // Increment score by 1 for each correct answer
            } else {
                results.add(new QuizResult("Question " + (i + 1), "Your answer: " + userAnswer,"Correct answer: "+ correctAnswer));
            }
        }

        scoreTextView.setText("Score: " + score + " / " + selectedAnswers.size());

        updateFeedbackMessage(score);
        return results;
    }

}

