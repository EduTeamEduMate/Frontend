package com.example.edumate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class ExamData implements Serializable {
    private String name;
    private int id;
    private List<Question> questions;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject examJson = new JSONObject();
        examJson.put("name", this.name);
        examJson.put("user_id", this.id); // Assuming `id` in ExamData is the user_id

        JSONArray questionsArray = new JSONArray();
        for (Question question : this.questions) {
            JSONObject questionJson = new JSONObject();
            questionJson.put("question_text", question.getQuestionText());
            questionJson.put("correct_answer", question.getCorrectAnswer());
            questionJson.put("false_answer_1", question.getFalseAnswer1());
            questionJson.put("false_answer_2", question.getFalseAnswer2());
            questionJson.put("false_answer_3", question.getFalseAnswer3());

            questionsArray.put(questionJson);
        }

        examJson.put("questions", questionsArray);
        return examJson;
    }
}