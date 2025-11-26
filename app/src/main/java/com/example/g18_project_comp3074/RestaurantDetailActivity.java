package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class RestaurantDetailActivity extends AppCompatActivity {

    private TextView textName, textAddress, textPhone, textNotes, textTags;
    private Button btnViewMap, btnDirections, btnShare, btnEdit, btnReturnHome;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbarDetail);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        textName = findViewById(R.id.textDetailName);
        textAddress = findViewById(R.id.textDetailAddress);
        textPhone = findViewById(R.id.textDetailPhone);
        textNotes = findViewById(R.id.textDetailNotes);
        textTags = findViewById(R.id.textDetailTags);

        btnViewMap = findViewById(R.id.btnViewMap);
        btnDirections = findViewById(R.id.btnDirections);
        btnShare = findViewById(R.id.btnShareEmail);
        btnEdit = findViewById(R.id.btnEditDetails);
        btnReturnHome = findViewById(R.id.btnReturnHome);

        if (getIntent() != null && getIntent().hasExtra("restaurant")) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
            if (restaurant != null) {
                textName.setText("Name: " + restaurant.getName());
                textAddress.setText("Address: " + restaurant.getAddress());
                textPhone.setText("Phone: " + restaurant.getPhone());
                textNotes.setText("Notes: " + restaurant.getNotes());
                textTags.setText("Tags: " + restaurant.getTags());
            }
        }

        btnViewMap.setOnClickListener(v -> {
            Intent i = new Intent(RestaurantDetailActivity.this, MapActivity.class);
            if (restaurant != null) {
                i.putExtra("address", restaurant.getAddress());
            }
            startActivity(i);
        });

        btnDirections.setOnClickListener(v -> {
            if (restaurant != null && restaurant.getAddress() != null) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurant.getAddress()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        btnShare.setOnClickListener(v -> {
            if (restaurant != null) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this restaurant: " + restaurant.getName());
                String body = "Name: " + restaurant.getName() + "\n"
                        + "Address: " + restaurant.getAddress() + "\n"
                        + "Phone: " + restaurant.getPhone() + "\n"
                        + "Tags: " + restaurant.getTags() + "\n\n"
                        + "Notes: " + restaurant.getNotes();
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(emailIntent);
            }
        });

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(RestaurantDetailActivity.this, RestaurantFormActivity.class);
            i.putExtra("restaurant", restaurant);
            startActivity(i);
        });

        btnReturnHome.setOnClickListener(v -> {
            Intent i = new Intent(RestaurantDetailActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}

