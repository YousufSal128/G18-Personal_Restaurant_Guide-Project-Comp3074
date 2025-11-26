package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class SearchResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        MaterialToolbar toolbar = findViewById(R.id.toolbarSearch);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView textQuery = findViewById(R.id.textQuery);
        TextView textResultsInfo = findViewById(R.id.textResultsInfo);

        String query = "";
        if (getIntent() != null && getIntent().hasExtra("query")) {
            query = getIntent().getStringExtra("query");
        }

        textQuery.setText("Query: " + query);
        textResultsInfo.setText("(This is where filtered restaurants would appear based on the query.)");
    }
}

