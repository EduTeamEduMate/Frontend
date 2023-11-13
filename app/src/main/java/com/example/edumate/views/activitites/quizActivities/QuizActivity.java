package com.example.edumate.views.activitites.quizActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.edumate.R;
import com.example.edumate.views.adapters.QuestionsTabAdapter;
import com.example.edumate.views.fragments.QuestionFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements QuestionFragment.OnAnswerSelectedListener{
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private ViewPager2 viewPager;
    private QuestionsTabAdapter adapter;
    private int[] userAnswers = new int[10];
    private boolean[] answersSelected;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);
        timerTextView = findViewById(R.id.timerTextView);
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        Button leftNavButton = findViewById(R.id.leftNavButton);
        Button rightNavButton = findViewById(R.id.rightNavButton);

        answersSelected = new boolean[10];
        Arrays.fill(answersSelected, false);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setEnabled(false);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);

                ArrayList<String> selectedAnswers = getSelectedAnswers();
                intent.putStringArrayListExtra("selectedAnswers", selectedAnswers);

                ArrayList<String> correctAnswers = getCorrectAnswers();
                intent.putStringArrayListExtra("correctAnswers", correctAnswers);

                startActivity(intent);
            }
        });

        adapter = new QuestionsTabAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Question " + (position + 1));
        }).attach();

        Arrays.fill(userAnswers, -1);

        setupButtonListeners(leftNavButton, rightNavButton);
        startTimer(300000);
    }


    @Override
    public void onAnswerSelected(int questionNumber, int selectedAnswerIndex) {
        userAnswers[questionNumber - 1] = selectedAnswerIndex;
        answersSelected[questionNumber - 1] = true;
        updateSubmitButtonState();
    }

    private void setupButtonListeners(Button leftNavButton, Button rightNavButton) {
        leftNavButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        rightNavButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });
    }

    private void startTimer(long timeLeftInMillis) {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timerTextView.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                autoSubmitQuiz();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private boolean areAllQuestionsAnswered() {
        for (int answer : userAnswers) {
            if (answer == -1) {
                return false;
            }
        }
        return true;
    }

    private void updateSubmitButtonState() {
        for (boolean isSelected : answersSelected) {
            if (!isSelected) {
                submitButton.setEnabled(false);
                return;
            }
        }
        submitButton.setEnabled(true);
    }

    private ArrayList<String> getSelectedAnswers() {
        ArrayList<String> selectedAnswers = new ArrayList<>();
        for (int answer : userAnswers) {
            selectedAnswers.add(answer == -1 ? null : String.valueOf(answer));
        }
        return selectedAnswers;
    }



    private ArrayList<String> getCorrectAnswers() {
        ArrayList<String> correctAnswers = new ArrayList<>();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            String correctAnswer = "Correct Answer for Question " + (i + 1);
            correctAnswers.add(correctAnswer);
        }

        return correctAnswers;
    }

    private void autoSubmitQuiz() {
        ArrayList<String> selectedAnswers = getSelectedAnswers();

        for (int i = 0; i < selectedAnswers.size(); i++) {
            if (selectedAnswers.get(i) == null) {
                selectedAnswers.set(i, "Unanswered");
            }
        }

        Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);
        intent.putStringArrayListExtra("selectedAnswers", selectedAnswers);
        intent.putStringArrayListExtra("correctAnswers", getCorrectAnswers());

        startActivity(intent);
        finish();
    }

}
