package com.example.edumate.views.activitites.quizActivities;

public class QuizResult {
    private String question;
    private String userAnswer;
    private String correctAnswer;

    public QuizResult(String question, String userAnswer, String correctAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public String getUserAnswer() { return userAnswer; }
    public String getCorrectAnswer() { return correctAnswer; }
}

