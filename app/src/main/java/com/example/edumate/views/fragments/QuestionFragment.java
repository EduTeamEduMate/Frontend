package com.example.edumate.views.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.RadioButton;

import com.example.edumate.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";
    private static final String ARG_QUESTION_TEXT = "question_text";
    private static final String ARG_ANSWERS = "answers";
    private RadioGroup radioGroupAnswers;

    private OnAnswerSelectedListener answerSelectedListener;
    private int questionNumber;

    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int questionNumber, int selectedAnswerIndex);
    }


    private void answerSelected(int selectedAnswerIndex) {
        if (answerSelectedListener != null) {
            answerSelectedListener.onAnswerSelected(questionNumber, selectedAnswerIndex);
        }
    }


    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(int questionNumber, String questionText, List<String> answers) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        args.putString(ARG_QUESTION_TEXT, questionText);
        args.putStringArrayList(ARG_ANSWERS, new ArrayList<>(answers));
        fragment.setArguments(args);
        return fragment;
    }

    private OnAnswerSelectedListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAnswerSelectedListener) {
            mListener = (OnAnswerSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAnswerSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        radioGroupAnswers = view.findViewById(R.id.radioGroupAnswers);
        return view;
    }
    public String getSelectedAnswer() {
        int selectedId = radioGroupAnswers.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = radioGroupAnswers.findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            int questionNumber = getArguments().getInt(ARG_QUESTION_NUMBER);
            String questionText = getArguments().getString(ARG_QUESTION_TEXT);
            List<String> answers = getArguments().getStringArrayList(ARG_ANSWERS);

            TextView textViewQuestionNumber = view.findViewById(R.id.textViewQuestionNumber);
            textViewQuestionNumber.setText(getString(R.string.question_number, questionNumber));

            TextView textViewQuestionText = view.findViewById(R.id.textViewQuestionText);
            textViewQuestionText.setText(questionText);

            RadioGroup radioGroupAnswers = view.findViewById(R.id.radioGroupAnswers);
            radioGroupAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int selectedAnswerIndex = group.indexOfChild(view.findViewById(checkedId));
                    if (mListener != null) {
                        mListener.onAnswerSelected(questionNumber, selectedAnswerIndex);
                    }
                }
            });

            for (int i = 0; i < answers.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setId(View.generateViewId());
                radioButton.setText(answers.get(i));

                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 0, 0, 50);

                radioGroupAnswers.addView(radioButton, layoutParams);
            }
        }
    }
}