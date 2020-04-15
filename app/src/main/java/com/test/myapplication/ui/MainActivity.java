package com.test.myapplication.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.myapplication.R;
import com.test.myapplication.model.LatLongResponse;
import com.test.myapplication.model.ShortAddressResponse;
import com.test.myapplication.utils.ApiClient;
import com.test.myapplication.utils.ApiInterface;
import com.test.myapplication.utils.Constants;

import java.text.DecimalFormat;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;
    DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);
    String shortCode = "";
    Marker marker;
    EditText txtSearchCode;
    Button btnSearch;
    Button btnDelivery;
    private ApiInterface apiInterface;
    private TextView tvShortAddress, tvLocationCoordinates;
    private GoogleMap mMap;
    private Button btnShareCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tvShortAddress = findViewById(R.id.short_address);
        tvLocationCoordinates = findViewById(R.id.location_coordinates);
        btnShareCode = findViewById(R.id.btn_share_address_code);
        txtSearchCode = findViewById(R.id.txtSearchCode);
        btnSearch = findViewById(R.id.btnSearch);
        btnDelivery = findViewById(R.id.btnDelivery);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;

        // RTL
        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("ar"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());


        //adham
        getSupportActionBar().hide();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnShareCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shortCode.equals(""))
                    shareShortAddress(shortCode);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtSearchCode.getText().toString().trim().equals("")) {
                    getLatLon(txtSearchCode.getText().toString().trim());
                }
            }
        });
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, DeliveryOptimizationActivity.class);
                startActivity(in);
            }
        });

    }

    public void getShortAddress(String location) {
        Call<ShortAddressResponse> call;
        call = apiInterface.getShortAddress(location);
        call.enqueue(new Callback<ShortAddressResponse>() {
            @Override
            public void onResponse(Call<ShortAddressResponse> call, Response<ShortAddressResponse> response) {
                if (response.isSuccessful()) {
                    shortCode = response.body().getAddress().getShortCode();
                    btnShareCode.setVisibility(View.VISIBLE);
                    tvShortAddress.setText(response.body().getAddress().getShortCode());
                }
            }

            @Override
            public void onFailure(Call<ShortAddressResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void getLatLon(String shortCode) {
        Call<LatLongResponse> call;
        call = apiInterface.getLatLon(shortCode, "ps");
        call.enqueue(new Callback<LatLongResponse>() {
            @Override
            public void onResponse(Call<LatLongResponse> call, Response<LatLongResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        String latlon = response.body().getAddress().getLatlon();
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latlon);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    } else {
                        // something wrong in short code.
                    }
                }
            }

            @Override
            public void onFailure(Call<LatLongResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String formattedLocationToDisplay = decimalFormat.format(location.getLatitude()) + "," + decimalFormat.format(location.getLongitude());
        String locationString = location.getLatitude() + "," + location.getLongitude();
        tvLocationCoordinates.setText(formattedLocationToDisplay);
        // Display location on a map
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("My current location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
        getShortAddress(locationString);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkLocationPermission();
    }

    private void shareShortAddress(String shortAddress) {
        String shareBody = "Your short address code: " + shortAddress;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_title));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }
}
