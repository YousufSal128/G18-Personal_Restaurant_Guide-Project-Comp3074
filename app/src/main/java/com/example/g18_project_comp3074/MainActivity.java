package com.example.g18_project_comp3074;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editSearch;
    private Button btnSearch, btnAddRestaurant, btnAbout;
    private ListView listRestaurants;

    private ArrayList<Restaurant> restaurantObjects = new ArrayList<>();
    private ArrayList<String> restaurantNames = new ArrayList<>();
    private RestaurantListAdapter adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.editSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        btnAbout = findViewById(R.id.btnAbout);
        listRestaurants = findViewById(R.id.listRestaurants);

        dbHelper = new DBHelper(this);
        adapter = new RestaurantListAdapter(this, restaurantNames);
        listRestaurants.setAdapter(adapter);
        loadRestaurants();

        btnAddRestaurant.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RestaurantFormActivity.class);
            startActivity(i);
        });

        btnAbout.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
        });

        btnSearch.setOnClickListener(v -> {
            String q = editSearch.getText().toString().trim();
            loadRestaurantList(q);
        });

        listRestaurants.setOnItemClickListener((parent, view, position, id) -> {
           Restaurant selectedRestaurant = restaurantObjects.get(position);
           Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
           intent.putExtra("restaurant", selectedRestaurant);
           startActivity(intent);
        });

//        btnSearch.setOnClickListener(v -> {
//            String query = editSearch.getText().toString().trim();
//            Intent i = new Intent(MainActivity.this, SearchResultsActivity.class);
//            i.putExtra("query", query);
//            startActivity(i);
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRestaurants();
    }

    private void loadRestaurants() {
        loadRestaurantList("");
    }

    private void loadRestaurantList(String s) {
        Cursor cursor;
        if (s.isEmpty()) {
            cursor = dbHelper.getAllRestaurants();
        } else {
            cursor = dbHelper.searchRestaurantsByName(s);
        }

        restaurantObjects.clear();
        restaurantNames.clear();

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"));
                String tags = cursor.getString(cursor.getColumnIndexOrThrow("tags"));

                Restaurant restaurant = new Restaurant(id, name, address, phone, notes, tags);
                restaurantObjects.add(restaurant);
                restaurantNames.add(name);
            }while(cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }
}

