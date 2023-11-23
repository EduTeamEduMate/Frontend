package com.example.edumate.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {
    private String question_text;
    private String correct_answer;
    private String false_answer_1;
    private String false_answer_2;
    private String false_answer_3;
    private int id;

    // Constructor
    public Question(String question_text, String correct_answer, String false_answer_1, String false_answer_2, String false_answer_3, int id) {
        this.question_text = question_text;
        this.correct_answer = correct_answer;
        this.false_answer_1 = false_answer_1;
        this.false_answer_2 = false_answer_2;
        this.false_answer_3 = false_answer_3;
        this.id = id;
    }
    public List<String> getOptions() {
        List<String> options = new ArrayList<>();
        options.add(correct_answer);
        options.add(false_answer_1);
        options.add(false_answer_2);
        options.add(false_answer_3);
        Collections.shuffle(options); // Optional: shuffle the options if you want them in random order
        return options;
    }

    // Getters and setters
    public String getQuestionText() {
        return question_text;
    }

    public void setQuestionText(String question_text) {
        this.question_text = question_text;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getFalseAnswer1() {
        return false_answer_1;
    }

    public void setFalseAnswer1(String false_answer_1) {
        this.false_answer_1 = false_answer_1;
    }

    public String getFalseAnswer2() {
        return false_answer_2;
    }

    public void setFalseAnswer2(String false_answer_2) {
        this.false_answer_2 = false_answer_2;
    }

    public String getFalseAnswer3() {
        return false_answer_3;
    }

    public void setFalseAnswer3(String false_answer_3) {
        this.false_answer_3 = false_answer_3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}