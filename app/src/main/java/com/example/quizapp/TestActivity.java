package com.example.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestActivity extends AppCompatActivity {
        private RecyclerView testView;
        private Toolbar toolbar;
        private List<TestModel> testList = new ArrayList<>();
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        int cat_index = getIntent().getIntExtra("CAT_INDEX", 0);
        getSupportActionBar().setTitle(CategoryFragment.catList.get(cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testView = findViewById(R.id.test_recycler_view);

        LinearLayoutManager  layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testView.setLayoutManager(layoutManager);

        loadData();
        TestAdapter adapter = new TestAdapter(testList);
        testView.setAdapter(adapter);
        Log.d("TestActivity", "Test List Size: " + testList.size());
            Log.d("TestActivity", "Category Index: " + cat_index);
            Log.d("TestActivity", "Category Name: " + CategoryFragment.catList.get(cat_index).getName());


        }
    public void loadData(){
            testList.add(new TestModel("Test 1", 10, 10));
            testList.add(new TestModel("Test 2", 10, 10));
            testList.add(new TestModel("Test 3", 10, 10));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                TestActivity.this.finish();
            }
        return super.onOptionsItemSelected(item);
    }
}