package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.utilities.Score;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {
private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
private Button leaderB, reAttemptB, viewAnsB;
private long timeTaken;
private Dialog progressDialog;
private TextView progressText;
private Toolbar toolbar;
private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(DBQuery.get_catList.get(DBQuery.get_selected_cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        Objects.requireNonNull(progressDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressText = progressDialog.findViewById(R.id.dialogText);
        progressText.setText("Loading...");
        progressDialog.show();

        init();
        loadData();

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, AnswersActivity.class);
                startActivity(intent);
            }

        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reAttempt();
            }
        });

        saveData();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void init(){
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.timeTaken);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.num_correct);
        wrongQTV = findViewById(R.id.num_wrong);
        unattemptedQTV = findViewById(R.id.num_unattempted);
//        leaderB = findViewById(R.id.leaderboardB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answer);
    }

    private void loadData(){
        int correctQ=0, wrongQ=0, unattemptedQ=0;
        for(int i = 0; i < DBQuery.get_questionList.size(); i++){
            if(DBQuery.get_questionList.get(i).getSelectedAnswer() == -1){
                unattemptedQ++;
            }else{
                if(DBQuery.get_questionList.get(i).getSelectedAnswer() == DBQuery.get_questionList.get(i).getCorrectAnswer()){
                    correctQ++;
                }else{
                    wrongQ++;
                }
            }
        }
        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptedQ));

        totalQTV.setText(String.valueOf(DBQuery.get_questionList.size()));
        finalScore = (correctQ * 100) / DBQuery.get_questionList.size(); // showing final score in percentage
        scoreTV.setText(String.valueOf(finalScore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);
        String time = String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken)));

        timeTV.setText(time);

    }

    private void reAttempt(){
        for (int i =0; i < DBQuery.get_questionList.size(); i++){
            DBQuery.get_questionList.get(i).setSelectedAnswer(-1);
            DBQuery.get_questionList.get(i).setStatus(DBQuery.NOT_VISITED);
        }
        Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveData(){

        DBQuery.saveResult(finalScore, new CompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(ScoreActivity.this, "Something wrong happened! Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}