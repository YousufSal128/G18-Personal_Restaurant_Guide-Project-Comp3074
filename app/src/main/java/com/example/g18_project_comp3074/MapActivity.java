package com.example.g18_project_comp3074;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String restaurantAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MaterialToolbar toolbar = findViewById(R.id.toolbarMap);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        if (getIntent() != null && getIntent().hasExtra("address")) {
            restaurantAddress = getIntent().getStringExtra("address");
            toolbar.setTitle(restaurantAddress); // Set the toolbar title to the address
        } else {
            toolbar.setTitle("Unknown Location");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!restaurantAddress.isEmpty()) {
            LatLng location = getLocationFromAddress(restaurantAddress);

            if (location != null) {
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title("Restaurant Location"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            } else {
                Toast.makeText(this, "Could not find coordinates for: " + restaurantAddress, Toast.LENGTH_LONG).show();
            }
        }
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = coder.getFromLocationName(strAddress, 1);
            if (addresses == null || addresses.isEmpty()) {
                return null;
            }

            Address location = addresses.get(0);
            //
            return new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Geocoding service error. Check network connection.", Toast.LENGTH_SHORT).show();
            return null;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}