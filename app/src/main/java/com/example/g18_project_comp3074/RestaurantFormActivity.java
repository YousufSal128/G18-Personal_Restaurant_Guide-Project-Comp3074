package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.List;

public class RestaurantFormActivity extends AppCompatActivity {

    private EditText editName, editAddress, editPhone, editNotes, editTags;
    private RatingBar ratingBarInput;
    private Button btnSaveRestaurant;
    private Restaurant restaurantToEdit;

    private DBHelper dbHelper;

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
        ratingBarInput = findViewById(R.id.ratingBarInput);
        btnSaveRestaurant = findViewById(R.id.btnSaveRestaurant);

        dbHelper = new DBHelper(this);

        // If editing, prefill
        if (getIntent() != null && getIntent().hasExtra("restaurant")) {
            restaurantToEdit = (Restaurant) getIntent().getSerializableExtra("restaurant");
            if (restaurantToEdit != null) {
                editName.setText(restaurantToEdit.getName());
                editAddress.setText(restaurantToEdit.getAddress());
                editPhone.setText(restaurantToEdit.getPhone());
                editNotes.setText(restaurantToEdit.getNotes());
                editTags.setText(restaurantToEdit.getTags());
                ratingBarInput.setRating(restaurantToEdit.getRating());
            }
        }

        btnSaveRestaurant.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String address = editAddress.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String notes = editNotes.getText().toString().trim();
            String tags = editTags.getText().toString().trim();
            float rating = ratingBarInput.getRating();

            double latitude = 0.0;
            double longitude = 0.0;

            try{
                Geocoder geocoder = new Geocoder(this);
                List<Address> locations = geocoder.getFromLocationName(address, 1);
                if (!locations.isEmpty()){
                    latitude = locations.get(0).getLatitude();
                    longitude = locations.get(0).getLongitude();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            if (restaurantToEdit != null) {
                boolean success = dbHelper.updateRestaurant(
                        restaurantToEdit.getId(),
                        name,
                        phone,
                        notes,
                        tags,
                        address,
                        latitude,
                        longitude,
                        rating
                );
            } else {
                dbHelper.addRestaurant(name, phone, tags, notes, address, latitude, longitude, rating);
            }

            Restaurant restaurant;
            if (restaurantToEdit != null) {
                restaurant = new Restaurant(restaurantToEdit.getId(), name, address, phone, notes, tags, rating);
            } else {
                restaurant = new Restaurant(name, address, phone, notes, tags, rating);
            }

            Intent i = new Intent(RestaurantFormActivity.this, RestaurantDetailActivity.class);
            i.putExtra("restaurant", restaurant);
            startActivity(i);
            finish();
        });
    }
}