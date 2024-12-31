package com.example.quizapp;

import static com.example.quizapp.DBQuery.ANSWERED;
import static com.example.quizapp.DBQuery.NOT_VISITED;
import static com.example.quizapp.DBQuery.REVIEW;
import static com.example.quizapp.DBQuery.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class QuestionGridAdapter extends BaseAdapter {
    private int numOfQuestions;
    private Context context;
    public QuestionGridAdapter(Context context, int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
        this.context = context;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myView;
        if (view == null) {
            myView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item, viewGroup, false);
        }
        else{
            myView = view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof QuestionActivity){
                    ((QuestionActivity)context).goToQuestion(i);
                }
            }
        });
        // TODO: Implement question view for each grid item
        TextView quesTV = myView.findViewById(R.id.ques_num);
        quesTV.setText(String.valueOf(i+1));

        switch (DBQuery.get_questionList.get(i).getStatus()){
            case NOT_VISITED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.gray)));
                break;
            case UNANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.red)));
                break;
            case ANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.green)));
                break;
            case REVIEW:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.pink)));
                break;
            default:
                break;
        }

        return myView;
    }
}
