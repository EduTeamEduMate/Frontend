package com.example.edumate.views.activitites.mainScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.edumate.R;
import com.example.edumate.views.activitites.QuizInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class GenerativeQuizFromTextInputActivity extends Activity {

    private ImageView backButtonTextInput;
    private EditText additionalTextInput;
    private Button generateButtonTextInput;
    private EditText examTitleEditTextInput;
    private static final String URL_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_quiz_from_text_input); // Make sure the XML file is named "activity_upload_notes.xml"

        // Initialize Views
        backButtonTextInput = findViewById(R.id.backButtonTextInput);
        generateButtonTextInput = findViewById(R.id.generateButtonTextInput);
        examTitleEditTextInput = findViewById(R.id.examTitleEditTextInput);
        additionalTextInput = findViewById(R.id.additionalTextInput);
        // Set onClick listeners
        backButtonTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes the activity and returns to the previous one
            }
        });



        generateButtonTextInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String title = examTitleEditTextInput.getText().toString().trim();
//                String textInput = additionalTextInput.getText().toString().trim();
//                if (title.isEmpty() || textInput.isEmpty()) {
//                    Toast.makeText(GenerativeQuizFromTextInputActivity.this, "Please enter a title for your quiz.", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent generateExam = new Intent(GenerativeQuizFromTextInputActivity.this, QuizInfoActivity.class);
//                    startActivity(generateExam);
//                }
                chatGPT(v);
            }





        });
    }
    public void chatGPT(View view) {
        EditText editText = findViewById(R.id.additionalTextInput);
        String prompt = editText.getText().toString();
        prompt += "write 10 mcq question from the following text given these constrainlts:[" +
                "there are 4 choices a,b,c,d; the correct choice is laways the first choice;" +
                " All questions start with (What, Which, In the, how) and ends with ?; there are 10 mcq questions."+
                " as a single string with newline characters separating the lines]" + prompt;

        //System.out.println(prompt);
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
                            TextView textView = findViewById(R.id.examTitleTextInput);
                            textView.setText(responseText);
                            System.out.println(responseText);
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


            int intTimeoutPeriod = 3* (60000); // 60 seconds timeout duration defined
            RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjectRequest.setRetryPolicy(retryPolicy);
            Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



}

