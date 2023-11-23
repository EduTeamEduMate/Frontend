package com.example.edumate.views.activitites.mainScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.edumate.models.ExamData;
import com.example.edumate.models.Question;
import com.example.edumate.views.activitites.GeneratingExamLoader;
import com.example.edumate.views.activitites.QuizInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GenerativeQuizFromTextInputActivity extends Activity {

    private ImageView backButtonTextInput;
    private EditText additionalTextInput;
    private Button generateButtonTextInput;
    private EditText examTitleEditTextInput;

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
                String title = examTitleEditTextInput.getText().toString().trim();
                String textInput = additionalTextInput.getText().toString().trim();

                if (title.isEmpty() || textInput.isEmpty()) {
                    Toast.makeText(GenerativeQuizFromTextInputActivity.this, "Please enter a title and additional text for your quiz.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent generateExam = new Intent(GenerativeQuizFromTextInputActivity.this, GeneratingExamLoader.class);
                    generateExam.putExtra("EXAM_TITLE", title);
                    generateExam.putExtra("ADDITIONAL_TEXT", textInput);
                    startActivity(generateExam);
                }
            }
        });



    }

    public void test(){
        String responseText = "What is the main source of light and heat in our solar system?\na. The sun\nb. The moon\nc. The stars\nd. The Earth\n\nHow does the sun provide energy to Earth?\na. Through photosynthesis\nb. Through nuclear fusion\nc. Through burning fossil fuels\nd. Through volcanic activity\n\nIn the morning, the sun appears to rise in the?\na. East\nb. West\nc. North\nd. South\n\nWhat is the distance between the Earth and the sun?\na. 93 million miles\nb. 1 million miles\nc. 500 thousand miles\nd. 10 million miles\n\nHow does the sun affect Earth's weather?\na. By providing warmth and energy\nb. By causing hurricanes and tornadoes\nc. By melting polar ice caps\nd. By creating earthquakes\n\nWhat is the average temperature of the sun's surface?\na. 10,000 degrees Fahrenheit\nb. 100 degrees Fahrenheit\nc. -100 degrees Fahrenheit\nd. 1 million degrees Fahrenheit\n\nIn the sun, nuclear fusion converts hydrogen into what element?\na. Helium\nb. Oxygen\nc. Carbon\nd. Nitrogen\n\nWhat is the lifespan of the sun expected to be?\na. About 5 billion years\nb. About 1 million years\nc. About 100 years\nd. About 50 billion years\n\nWhat is the layer of the sun's atmosphere that gives it a glowing appearance?\na. The photosphere\nb. The corona\nc. The chromosphere\nd. The flaresphere\n\nHow does the sun's gravity affect the planets in our solar system?\na. It keeps them in orbit\nb. It pushes them away\nc. It pulls them towards the Earth\nd. It has no gravitational effect on them"; // Replace with your actual response text
        List<Question> questionList = new ArrayList<>();

// Split the text into individual questions
        String[] rawQuestions = responseText.split("\\n\\n");

        int questionId = 1; // Starting ID for questions
        for (String rawQuestion : rawQuestions) {
            String[] lines = rawQuestion.split("\\n");

            if (lines.length < 5) continue; // Skip if there are not enough lines for a question and 4 options

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

// Set name and id for ExamData as required

    }




}

