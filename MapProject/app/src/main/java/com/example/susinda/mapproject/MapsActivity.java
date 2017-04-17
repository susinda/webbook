package com.example.susinda.mapproject;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MapsActivity extends FragmentActivity implements LocationListener {

    String backend = "http://lkauto.org/webbook.api-1.0/user/service/location?phone=";
    String locationUpdateUrl = "http://lkauto.org/webbook.api-1.0/user/service/location";
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    OKHttpClientWrapper helper;

    //////////////////// Listeners and call backs ///////////////////////////////////////////
    OnClickListener findClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Getting reference to EditText to get the user input location
            EditText etPhone = (EditText) findViewById(R.id.et_location);
            String phoneNumber = etPhone.getText().toString();
            try {
                //helper.doPost(locationUpdateUrl, postLocationCallback);
                helper.doGet(backend + phoneNumber, getLocationCallback);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    final Callback getLocationCallback = new Callback() {
        @Override
        public void onFailure(final Call call, IOException e) {
            Toast.makeText(MapsActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            String responseString = response.body().string();
            try {
                JSONObject jsonObject = new JSONObject(responseString);
                double lat = jsonObject.getDouble("Lat");
                double lon = jsonObject.getDouble("Lon");
                final LatLng dbLocation = new LatLng(lat, lon);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateMarker(dbLocation);
                        updateCamera(dbLocation);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        EditText etPhone = (EditText) findViewById(R.id.et_location);
        String phoneNumber = etPhone.getText().toString();
        try {
            helper.doPost(locationUpdateUrl, postLocationCallback, phoneNumber, (float)latitude, (float)longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: " + latitude;
        Log.i("log", s);
    }


    final Callback postLocationCallback = new Callback() {
        @Override
        public void onFailure(final Call call, IOException e) {
            Toast.makeText(MapsActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(Call call, final Response response) throws IOException {
            String responseString = response.body().string();
            try {
                JSONObject jsonObject = new JSONObject(responseString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    //////////////////// Listeners and call backs ///////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        helper = new OKHttpClientWrapper();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
        EditText etLocation = (EditText) findViewById(R.id.et_location);
        etLocation.setText("716049075");

        // Getting reference to btn_find of the layout activity_main
        Button btn_find = (Button) findViewById(R.id.btn_find);
        btn_find.setOnClickListener(findClickListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

    private void updateMarker(LatLng mLatLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(mLatLng));
    }

    private void updateCamera(LatLng mLatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
