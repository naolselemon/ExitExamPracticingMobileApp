package com.example.quizapp;

import static com.example.quizapp.DBQuery.NOT_VISITED;
import static com.example.quizapp.DBQuery.UNANSWERED;
import static com.example.quizapp.DBQuery.get_catList;
import static com.example.quizapp.DBQuery.get_questionList;
import static com.example.quizapp.DBQuery.get_selected_cat_index;
import static com.example.quizapp.DBQuery.get_selected_test_index;
import static com.example.quizapp.DBQuery.get_testList;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView questionView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    QuestionsAdapter questionsAdapter;
    private int quesID;
    private DrawerLayout drawer;
    private ImageButton drawerCloseB;
    private GridView quesListGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.questions_list_layout);

        init();


        questionsAdapter = new QuestionsAdapter(get_questionList);
        questionView.setAdapter(questionsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionView.setLayoutManager(layoutManager);

        setSnapHelper();
        setClickListener();
        startTimer();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init(){
        questionView = findViewById(R.id.rv_question);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV = findViewById(R.id.tv_timer);
        catNameTV = findViewById(R.id.tv_catName);
        submitB = findViewById(R.id.submitB);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clearB);
        prevQuesB = findViewById(R.id.previousB);
        nextQuesB = findViewById(R.id.nextB);
        quesListB = findViewById(R.id.quesListGrid);
        drawer = findViewById(R.id.drawer_layout);
//        drawerCloseB = findViewById(R.id.drawerCloseB);
        quesID = 0;
        tvQuesID.setText(String.format("1/%s", String.valueOf(get_questionList.size())));
        catNameTV.setText(get_catList.get(get_selected_cat_index).getName());
    }


    //snapHelper is used to control scrolling horizontal over the each question
    private void setSnapHelper(){
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionView);

       questionView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);

              View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
              quesID = recyclerView.getLayoutManager().getPosition(view);

              if (get_questionList.get(quesID).getStatus() == NOT_VISITED){
                  get_questionList.get(quesID).setStatus(UNANSWERED);
              }

              tvQuesID.setText(String.format("%s/%s", String.valueOf(quesID+1), String.valueOf(get_questionList.size())));

           }

           @Override
           public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
           }
       });
    }
    private void setClickListener(){
        prevQuesB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(quesID > 0){
                    quesID--;
                    questionView.smoothScrollToPosition(quesID);
                }
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (quesID < get_questionList.size()-1){
                    quesID++;
                    questionView.smoothScrollToPosition(quesID);
                }
            }
        });

        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_questionList.get(quesID).setSelectedAnswer(-1);
                questionsAdapter.notifyDataSetChanged();
            }
        });

        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (! drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

//        drawerCloseB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(drawer.isDrawerOpen(GravityCompat.END)){
//                    drawer.closeDrawer(GravityCompat.END);
//                }
//            }
//        });
    }

    private void startTimer(){
        //Calculating total time if each question is given one minutes --- getTime() uses milliseconds
       long totalTime = (long) get_testList.get(get_selected_test_index).getTime() *60*1000;

       //decrementing total time per seconds(1000 millis)
        CountDownTimer timer = new CountDownTimer(totalTime + 1000, 1000) { // here on the totalTime 1000 is added this because once start button is clicked timer starts but question interface takes some seconds to be displayed(1 seconds GUI)
            @Override
            public void onTick(long remainingTime) {

                //here Once the exam started timer starts count down. In the below code I am formatting how the timer
                // displays minute and seconds by calculating them from milliseconds
                String time = String.format("%02d:%02d min", TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                            TimeUnit.MILLISECONDS.toSeconds(remainingTime)-
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));

                timerTV.setText(time);
            };

            @Override
            public void onFinish() {

            }
        };
        timer.start();

    }
}