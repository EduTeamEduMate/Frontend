package com.example.edumate.views.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.edumate.views.fragments.QuestionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionsTabAdapter extends FragmentStateAdapter {
    private final List<String> questionTexts;
    private final List<List<String>> allAnswers;

    public QuestionsTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        questionTexts = new ArrayList<>();
        allAnswers = new ArrayList<>();
        initializeDummyData();
    }

    @Override
    public Fragment createFragment(int position) {
        String questionText = questionTexts.get(position);
        List<String> answers = allAnswers.get(position);
        Log.d("QuestionFragment", "Creating fragment for position: " + position + " with question: " + questionText);
        return QuestionFragment.newInstance(position + 1, questionText, answers);
    }


    @Override
    public int getItemCount() {
        return questionTexts.size();
    }

    private void initializeDummyData() {
        for (int i = 0; i < 10; i++) {
            questionTexts.add("What is the capital of country " + (i + 1) + "?");
            allAnswers.add(Arrays.asList(
                    "Answer A",
                    "Answer B",
                    "Answer C",
                    "Answer D"
            ));
        }
    }
}
