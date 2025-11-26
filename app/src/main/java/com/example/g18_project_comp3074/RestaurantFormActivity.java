package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

public class RestaurantFormActivity extends AppCompatActivity {

    private EditText editName, editAddress, editPhone, editNotes, editTags;
    private Button btnSaveRestaurant;
    private Restaurant restaurantToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);

        MaterialToolbar toolbar = findViewById(R.id.toolbarForm);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editPhone = findViewById(R.id.editPhone);
        editNotes = findViewById(R.id.editNotes);
        editTags = findViewById(R.id.editTags);
        btnSaveRestaurant = findViewById(R.id.btnSaveRestaurant);

        // If editing, prefill
        if (getIntent() != null && getIntent().hasExtra("restaurant")) {
            restaurantToEdit = (Restaurant) getIntent().getSerializableExtra("restaurant");
            if (restaurantToEdit != null) {
                editName.setText(restaurantToEdit.getName());
                editAddress.setText(restaurantToEdit.getAddress());
                editPhone.setText(restaurantToEdit.getPhone());
                editNotes.setText(restaurantToEdit.getNotes());
                editTags.setText(restaurantToEdit.getTags());
            }
        }

        btnSaveRestaurant.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String address = editAddress.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String notes = editNotes.getText().toString().trim();
            String tags = editTags.getText().toString().trim();

            Restaurant restaurant = new Restaurant(name, address, phone, notes, tags);

            Intent i = new Intent(RestaurantFormActivity.this, RestaurantDetailActivity.class);
            i.putExtra("restaurant", restaurant);
            startActivity(i);
        });
    }
}
