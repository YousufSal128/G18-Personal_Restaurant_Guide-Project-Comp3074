package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMap);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView txt = findViewById(R.id.textMapInfo);

        String address = "";
        if (getIntent() != null && getIntent().hasExtra("address")) {
            address = getIntent().getStringExtra("address");
        }

        txt.setText("Map for this restaurant would appear here.\n\nAddress:\n" + address);
    }
}

