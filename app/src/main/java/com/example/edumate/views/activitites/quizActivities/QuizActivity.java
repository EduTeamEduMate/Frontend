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
import com.example.edumate.models.ExamData;
import com.example.edumate.models.Question;
import com.example.edumate.views.adapters.QuestionsTabAdapter;
import com.example.edumate.views.fragments.QuestionFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements QuestionFragment.OnAnswerSelectedListener{

    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private ViewPager2 viewPager;
    private QuestionsTabAdapter adapter;
    private List<Question> questions;
    private String[] userAnswers;
    private boolean[] answersSelected;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_activity);

        initializeViews();
        receiveAndSetupExamData();
        setupButtonListeners();
        startTimer(300000); // 5 minutes timer
    }

    private void initializeViews() {
        timerTextView = findViewById(R.id.timerTextView);
        viewPager = findViewById(R.id.viewPager);
        submitButton = findViewById(R.id.submitButton);
//        submitButton.setEnabled(false);
    }

    private void receiveAndSetupExamData() {
        ExamData examData = (ExamData) getIntent().getSerializableExtra("EXAM_DATA");
        if (examData != null) {
            this.questions = examData.getQuestions();
            userAnswers = new String[questions.size()];
            Arrays.fill(userAnswers, null);
            answersSelected = new boolean[questions.size()];
            setupViewPagerAndViewPagerTabs();
        } else {
            // Handle error: Exam data is missing
        }
    }
    private void setupViewPagerAndViewPagerTabs() {
        adapter = new QuestionsTabAdapter(this, questions);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText("Question " + (position + 1))).attach();
    }

    private void setupButtonListeners() {
        Button leftNavButton = findViewById(R.id.leftNavButton);
        Button rightNavButton = findViewById(R.id.rightNavButton);
        setupButtonListeners(leftNavButton, rightNavButton);
        // Implement navigation button listeners
        // Implement submit button listener
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, ResultsActivity.class);

                ArrayList<String> selectedAnswers = getSelectedAnswers();
                intent.putStringArrayListExtra("selectedAnswers", selectedAnswers);

                ArrayList<String> correctAnswers = getCorrectAnswers();
                intent.putStringArrayListExtra("correctAnswers", correctAnswers);

                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onAnswerSelected(int questionNumber, String selectedAnswer) {
        userAnswers[questionNumber - 1] = selectedAnswer;
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
//
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


    private void updateSubmitButtonState() {
        for (String answer : userAnswers) {
            if (answer == null) {
                submitButton.setVisibility(View.GONE); // Hide button if any question is unanswered
                return;
            }
        }
        submitButton.setVisibility(View.VISIBLE); // Show button if all questions are answered
    }


    private ArrayList<String> getSelectedAnswers() {
        return new ArrayList<>(Arrays.asList(userAnswers));
    }



    private ArrayList<String> getCorrectAnswers() {
        ArrayList<String> correctAnswers = new ArrayList<>();

        for (Question question : questions) {
            correctAnswers.add(question.getCorrectAnswer());
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
