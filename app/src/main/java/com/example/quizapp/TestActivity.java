package com.example.quizapp;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Adapter.TestAdapter;

import java.util.Objects;

public class TestActivity extends AppCompatActivity {
        private RecyclerView testView;
        private Toolbar toolbar;
        private TestAdapter adapter;
        private Dialog progressDialog;
        private TextView progressText;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
//        int cat_index = getIntent().getIntExtra("CAT_INDEX", 0);
            if (DBQuery.get_catList.isEmpty()) {
                Log.e("TestActivity", "Category list is empty.");
                Toast.makeText(this, "Category list is empty!", Toast.LENGTH_SHORT).show();
                finish(); // Exit the activity
                return;
            }

            getSupportActionBar().setTitle(DBQuery.get_catList.get(DBQuery.get_selected_cat_index).getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            testView = findViewById(R.id.test_recycler_view);


            progressDialog = new Dialog(TestActivity.this);
            progressDialog.setContentView(R.layout.dialog_layout);
            progressDialog.setCancelable(false);
            Objects.requireNonNull(progressDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            progressText = progressDialog.findViewById(R.id.dialogText);
            progressText.setText("Loading...");
            progressDialog.show();

        LinearLayoutManager  layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testView.setLayoutManager(layoutManager);

//        loadData();
            DBQuery.loadTestData(new CompleteListener() {
                @Override
                public void onSuccess() {
                    DBQuery.loadCategories(new CompleteListener() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                            adapter = new TestAdapter(DBQuery.get_testList);
                            testView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure() {
                            progressDialog.dismiss();
                            Toast.makeText(TestActivity.this, "Something wrong happened! Try again later", Toast.LENGTH_SHORT).show();

                        }
                    });
//                    progressDialog.dismiss();
//                    adapter = new TestAdapter(DBQuery.get_testList);
//                    testView.setAdapter(adapter);
                }

                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                    Toast.makeText(TestActivity.this, "Something wrong happened! Try again later", Toast.LENGTH_SHORT).show();

                }
            });

//        Log.d("TestActivity", "Test List Size: " + testList.size());
//            Log.d("TestActivity", "Category Index: " + cat_index);
//            Log.d("TestActivity", "Category Name: " + DBQuery.get_catList.get(cat_index).getName());


        }
//    public void loadData(){
//            testList.add(new TestModel("Test 1", 10, 10));
//            testList.add(new TestModel("Test 2", 10, 10));
//            testList.add(new TestModel("Test 3", 10, 10));
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                TestActivity.this.finish();
            }
        return super.onOptionsItemSelected(item);
    }
}