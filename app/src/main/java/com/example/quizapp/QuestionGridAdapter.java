package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class QuestionGridAdapter extends BaseAdapter {
    private int numOfQuestions;
    public QuestionGridAdapter(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    @Override
    public int getCount() {
        return numOfQuestions;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View myView;
        if (view == null) {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item, viewGroup, false);
        }
        else{
            myView = view;
        }
        // TODO: Implement question view for each grid item

        return myView;
    }
}
