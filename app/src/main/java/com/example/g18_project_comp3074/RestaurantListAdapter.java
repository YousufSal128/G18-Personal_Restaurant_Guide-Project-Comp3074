package com.example.g18_project_comp3074;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RestaurantListAdapter extends ArrayAdapter<String> {

    public RestaurantListAdapter(Context context, ArrayList<String> list) {
        super(context, android.R.layout.simple_list_item_1, list);
    }
}
