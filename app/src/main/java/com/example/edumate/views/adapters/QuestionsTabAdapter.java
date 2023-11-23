package com.example.edumate.views.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.edumate.models.Question;
import com.example.edumate.views.fragments.QuestionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionsTabAdapter extends FragmentStateAdapter {
    private final List<Question> questionTexts;

    public QuestionsTabAdapter(@NonNull FragmentActivity fragmentActivity,  List<Question> questions) {
        super(fragmentActivity);
        this.questionTexts = questions;
    }

    @Override
    public Fragment createFragment(int position) {
        Question question = questionTexts.get(position);
        return QuestionFragment.newInstance(position + 1, question.getQuestionText(), question.getOptions());
    }


    @Override
    public int getItemCount() {
        return questionTexts.size();
    }

//    private void initializeDummyData() {
//        for (int i = 0; i < 10; i++) {
//            questionTexts.add("What is the capital of country " + (i + 1) + "?");
//            allAnswers.add(Arrays.asList(
//                    "Answer A",
//                    "Answer B",
//                    "Answer C",
//                    "Answer D"
//            ));
//        }
//    }
}
