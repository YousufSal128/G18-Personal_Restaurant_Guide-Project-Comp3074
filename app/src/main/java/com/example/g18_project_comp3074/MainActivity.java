package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editSearch;
    private Button btnSearch, btnAddRestaurant, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        btnAbout = findViewById(R.id.btnAbout);

        btnAddRestaurant.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RestaurantFormActivity.class);
            startActivity(i);
        });

        btnAbout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        });

        btnSearch.setOnClickListener(v -> {
            String query = editSearch.getText().toString().trim();
            Intent i = new Intent(MainActivity.this, SearchResultsActivity.class);
            i.putExtra("query", query);
            startActivity(i);
        });
    }
}

