package com.example.edumate.views.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edumate.R;
import com.example.edumate.models.ExamData;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;
import com.example.edumate.views.activitites.quizActivities.QuizActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        int examId = getIntent().getIntExtra("EXAM_ID", -1);
        if (examId != -1) {
            fetchExamData(examId);
        } else {
            Toast.makeText(this, "Invalid Exam ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void fetchExamData(int examId) {
        ApiService apiService = RetrofitClient.getApiService();

        Call<ExamData> call = apiService.getExamById(examId);
        call.enqueue(new Callback<ExamData>() {
            @Override
            public void onResponse(Call<ExamData> call, Response<ExamData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ExamData examData = response.body();
                    Intent intent = new Intent(LoadingActivity.this, QuizActivity.class);
                    intent.putExtra("EXAM_DATA", examData);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoadingActivity.this, "Failed to load exam data", Toast.LENGTH_SHORT).show();
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<ExamData> call, Throwable t) {
                Toast.makeText(LoadingActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // Handle network error
            }
        });
    }
}