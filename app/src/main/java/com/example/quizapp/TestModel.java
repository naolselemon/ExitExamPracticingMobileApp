package com.example.quizapp;

public class TestModel {

    private String testId;
    private int testScore;
    private int time;

    public TestModel(String testId, int testScore, int time) {
        this.testId = testId;
        this.testScore = testScore;
        this.time = time;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getTestScore() {
        return testScore;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
