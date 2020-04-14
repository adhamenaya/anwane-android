package com.test.myapplication.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.myapplication.R;
import com.test.myapplication.model.DeliveryOptimizationResponse;
import com.test.myapplication.model.LatLongResponse;
import com.test.myapplication.model.LocationsItem;
import com.test.myapplication.model.OptimizedDeliveryRequest;
import com.test.myapplication.utils.ApiClient;
import com.test.myapplication.utils.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryOptimizationActivity extends AppCompatActivity{

    private ApiInterface apiInterface;
    private EditText txtShortCode;
    private Button btnAddNewLatLon;
    private Button btnStartPlanning;
    private List<LocationsItem> locationsItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        txtShortCode = findViewById(R.id.txt_short_code);
        btnAddNewLatLon = findViewById(R.id.btn_append_locations);
        btnStartPlanning = findViewById(R.id.btn_start_planning);

        btnAddNewLatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtShortCode.getText().toString().trim().equals("")) {
                    getLatLon(txtShortCode.getText().toString().trim());
                }
            }
        });

        btnStartPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationsItems.size() > 0) {
                    optimizedDelivery(locationsItems);
                }
            }
        });


    }

    public void optimizedDelivery(List<LocationsItem> locationsItemList) {
        Call<DeliveryOptimizationResponse> call;

        OptimizedDeliveryRequest request = new OptimizedDeliveryRequest();
        request.setLocations(locationsItemList);
        call = apiInterface.deliveryOptimization(request);
        call.enqueue(new Callback<DeliveryOptimizationResponse>() {
            @Override
            public void onResponse(Call<DeliveryOptimizationResponse> call, Response<DeliveryOptimizationResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryOptimizationResponse> call, Throwable t) {
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
                    if(response.body().isSuccess()) {
                        String latlon = response.body().getAddress().getLatlon();
                        LocationsItem locationsItem = new LocationsItem();
                        locationsItem.setLatlon(latlon);
                        locationsItems.add(locationsItem);
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
}
