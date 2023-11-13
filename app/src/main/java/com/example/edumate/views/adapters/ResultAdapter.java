package com.example.edumate.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.edumate.R;
import com.example.edumate.views.activitites.quizActivities.QuizResult;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    private List<QuizResult> quizResults;

    public ResultAdapter(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuizResult quizResult = quizResults.get(position);
        holder.bind(quizResult);
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView, userAnswerTextView, correctAnswerTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            userAnswerTextView = itemView.findViewById(R.id.userAnswerTextView);
            correctAnswerTextView = itemView.findViewById(R.id.correctAnswerTextView);
        }

        public void bind(QuizResult quizResult) {
            questionTextView.setText(quizResult.getQuestion());
            userAnswerTextView.setText(quizResult.getUserAnswer());
            correctAnswerTextView.setText(quizResult.getCorrectAnswer());
        }
    }
}

