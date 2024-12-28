package com.example.quizapp;

import static com.example.quizapp.DBQuery.get_catList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class StartTestActivity extends AppCompatActivity {

    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;
    private Dialog progressDialog;
    private TextView progressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_test);

        progressDialog = new Dialog(StartTestActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        Objects.requireNonNull(progressDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressText = progressDialog.findViewById(R.id.dialogText);
        progressText.setText("Loading...");
        progressDialog.show();

        init();
        DBQuery.loadQuestions(new CompleteListener() {
            @Override
            public void onSuccess() {
                setData();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                progressDialog.dismiss();
                Toast.makeText(StartTestActivity.this, "Something wrong happened! Try again later", Toast.LENGTH_SHORT).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init(){
        catName = findViewById(R.id.st_categoryName);
        testNo = findViewById(R.id.st_testNo);
        totalQ = findViewById(R.id.st_totalQ);
        bestScore = findViewById(R.id.st_bestScore);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_testB);
        backB = findViewById(R.id.st_back);

        backB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                StartTestActivity.this.finish();
            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartTestActivity.this, QuestionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setData(){
        catName.setText(get_catList.get(DBQuery.get_selected_cat_index).getName());
        testNo.setText(String.format("Test Number: %s", String.valueOf(DBQuery.get_selected_test_index + 1)));
        totalQ.setText(String.valueOf(DBQuery.get_questionList.size()));
        bestScore.setText(String.valueOf(DBQuery.get_testList.get(DBQuery.get_selected_test_index).getTestScore()));
        time.setText(String.valueOf(DBQuery.get_testList.get(DBQuery.get_selected_test_index).getTime()));
    }
}