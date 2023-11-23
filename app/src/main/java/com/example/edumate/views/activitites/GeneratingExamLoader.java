package com.example.edumate.views.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edumate.R;
import com.example.edumate.models.ExamData;
import com.example.edumate.models.Question;
import com.example.edumate.models.SUserData;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneratingExamLoader extends AppCompatActivity {
    private static final String URL_ENDPOINT = "https://api.openai.com/v1/chat/completions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating_exam_loader);
        Intent intent = getIntent();
        String additionalText = intent.getStringExtra("ADDITIONAL_TEXT");
        chatGPT(additionalText);


    }

    public void chatGPT(String content) {
        String prompt = content;
        prompt += "write 10 mcq question from the following text given these constrainlts:[" +
                "there are 4 choices a,b,c,d; the correct choice is laways the first choice;" +
                " All questions start with (What, Which, In the, how) and ends with ?; there are 10 mcq questions." +
                " as a single string with newline characters separating the lines]" + prompt;

//        System.out.println(prompt);
        Toast.makeText(getApplicationContext(), prompt, Toast.LENGTH_LONG).show();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("model", "gpt-3.5-turbo"); // our Model Name

            JSONArray jsonArray = new JSONArray();
            JSONObject jMessage = new JSONObject();
            jMessage.put("role", "user");
            jMessage.put("content", prompt);
            jsonArray.put(jMessage);
            jsonObject.put("messages", jsonArray);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    URL_ENDPOINT,
                    jsonObject,
                    response -> {
                        try {
                            String responseText = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
//                            TextView textView = findViewById(R.id.examTitleTextInput);
//                            textView.setText(responseText);
                            System.out.println(responseText);
                            TextView check = findViewById(R.id.generatingExamTextView);
                            check.setText("done...");
                            test(responseText);
                            //parseToJSON(responseText);
                            // The Call for Parsing goes here.
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        String errorMessage = "Error Detected";
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                String errorString = new String(error.networkResponse.data);
                                JSONObject errorJSON = new JSONObject(errorString);
                                errorMessage = errorJSON.optString("error", errorMessage);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                        System.out.println(errorMessage);
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + "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"); // API key
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };


            int intTimeoutPeriod = 3 * (60000); // 60 seconds timeout duration defined
            RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    private RequestBody createRequestBody(JSONObject jsonObject) {
        return RequestBody.create(
                jsonObject.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );
    }


    public void test(String responseText) {
        List<Question> questionList = new ArrayList<>();

// Split the text into individual questions
        String[] rawQuestions = responseText.split("\\n\\n");

        int questionId = 1; // Starting ID for questions
        for (String rawQuestion : rawQuestions) {
            String[] lines = rawQuestion.split("\\n");

            if (lines.length < 5)
                continue; // Skip if there are not enough lines for a question and 4 options

            // Extract question and answers
            String questionText = lines[0];
            String[] answers = Arrays.copyOfRange(lines, 1, lines.length);

            // Assuming the first option is always the correct one
            String correctAnswer = answers[0].substring(3); // Skip the option label (e.g., "a. ")
            List<String> falseAnswers = Arrays.stream(answers, 1, answers.length)
                    .map(answer -> answer.substring(3))
                    .collect(Collectors.toList());

            // Create a new Question object
            Question question = new Question(questionText, correctAnswer,
                    falseAnswers.get(0), falseAnswers.get(1), falseAnswers.get(2),
                    questionId++);

            // Add to the list
            questionList.add(question);
        }

// Assuming you have an ExamData object
        ExamData examData = new ExamData();
        examData.setQuestions(questionList);
        examData.setId(SUserData.getInstance().getId());
        String examTitle = getIntent().getStringExtra("EXAM_TITLE");
        examData.setName(examTitle);


            try {
                JSONObject examDataJson = examData.toJson();
                RequestBody requestBody = createRequestBody(examDataJson);

                ApiService apiService = RetrofitClient.getApiService();
                Call<ResponseBody> call = apiService.postExam(requestBody);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            TextView check = findViewById(R.id.generatingExamTextView);
                            check.setText("done...");
                        } else {
                            TextView check = findViewById(R.id.generatingExamTextView);
                            check.setText("error...");                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        TextView check = findViewById(R.id.generatingExamTextView);
                        check.setText("error 2...");
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


}