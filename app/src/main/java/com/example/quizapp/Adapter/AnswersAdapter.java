package com.example.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Model.QuestionModel;
import com.example.quizapp.R;

import java.util.ArrayList;
import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder>  {
    public AnswersAdapter(List<QuestionModel> questionList) {
        this.questionList = questionList;
    }

    public List<QuestionModel> questionList =  new ArrayList<>();
    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {
        String ques = questionList.get(position).getQuestion();
        String a = questionList.get(position).getOptionA();
        String b = questionList.get(position).getOptionB();
        String c = questionList.get(position).getOptionC();
        String d = questionList.get(position).getOptionD();
        int selectedAns = questionList.get(position).getSelectedAnswer();
        int correctAns = questionList.get(position).getCorrectAnswer();

        holder.setData(position+1, ques, a, b, c, d, selectedAns, correctAns);
    }


    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questNo, question, optionA,  optionB, optionC, optionD, result;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            questNo = itemView.findViewById(R.id.question_number);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.opA);
            optionB = itemView.findViewById(R.id.opB);
            optionC = itemView.findViewById(R.id.opC);
            optionD = itemView.findViewById(R.id.opD);
            result = itemView.findViewById(R.id.result);
        }
        private void setData(int pos, String ques, String a, String b, String c, String d, int selected, int correctAnswer){
            questNo.setText(String.format("Question Number: %s", String.valueOf(pos)));
            question.setText(ques);
            optionA.setText(a);
            optionB.setText(b);
            optionC.setText(c);
            optionD.setText(d);

            if(selected == correctAnswer){
                result.setText("CORRECT");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                setOptionColor(selected, R.color.green);
            }else if(selected == -1){
                result.setText("UN-ANSWERED");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
            } else{
                result.setText("WRONG");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                setOptionColor(selected, R.color.red);
            }
        }

        private void setOptionColor(int selected, int color){

            switch (selected){
                case 1:
                    optionA.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 2:
                    optionB.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 3:
                    optionC.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 4:
                    optionD.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
            }
        }
    }

}
