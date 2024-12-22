package com.example.quizapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<TestModel>testList;

    public TestAdapter(List<TestModel> testList) {
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        int progress = testList.get(position).getTestScore();
        holder.setData(position, progress);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView testNo;
        private TextView topScore;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testNo = itemView.findViewById(R.id.testNo);
            topScore = itemView.findViewById(R.id.testPercent);
            progressBar = itemView.findViewById(R.id.testProgressbar);
        }

        private void setData(int pos,int progress){
            testNo.setText("Test Number: "+ String.valueOf(pos + 1));
            topScore.setText("Top Score: "+ String.valueOf(progress) + "%");
            progressBar.setProgress(progress);
            Log.d("TestAdapter", "Binding data for position: " + pos);
        }


    }
}
