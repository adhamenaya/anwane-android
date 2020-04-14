package com.test.myapplication.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.myapplication.R;
import com.test.myapplication.model.DeliveryOptimizationResponse;
import com.test.myapplication.model.DeliveryPlanItem;
import com.test.myapplication.model.LatLongResponse;
import com.test.myapplication.model.LocationsItem;
import com.test.myapplication.model.OptimizedDeliveryRequest;
import com.test.myapplication.ui.adapter.LocationsAdapter;
import com.test.myapplication.utils.ApiClient;
import com.test.myapplication.utils.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryOptimizationActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private EditText txtShortCode;
    private Button btnAddNewLatLon;
    private Button btnStartPlanning;
    private List<LocationsItem> locationsItems = new ArrayList<>();
    private RecyclerView recyclerViewLocations;
    private LocationsAdapter locationsAdapter;
    private ImageView imageViewSpeechInput;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        txtShortCode = findViewById(R.id.txt_short_code);
        btnAddNewLatLon = findViewById(R.id.btn_append_locations);
        btnStartPlanning = findViewById(R.id.btn_start_planning);
        recyclerViewLocations = findViewById(R.id.recycler_view_locations);
        imageViewSpeechInput = findViewById(R.id.imageview_mic);
        locationsAdapter = new LocationsAdapter(getApplicationContext(), locationsItems);
        configureLocationsList();
        btnAddNewLatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtShortCode.getText().toString().trim().equals("")) {
                    getLatLon(txtShortCode.getText().toString().trim());
                }
            }
        });

        btnStartPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationsItems.size() > 0) {
                    optimizedDelivery(locationsItems);
                }
            }
        });

        imageViewSpeechInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

    }

    private void configureLocationsList() {
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewLocations.setAdapter(locationsAdapter);
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtShortCode.setText(result.get(0).replaceAll(" ", ""));
                }
                break;
            }

        }
    }

    public void optimizedDelivery(List<LocationsItem> locationsItemList) {
        Call<DeliveryOptimizationResponse> call;
        OptimizedDeliveryRequest request = new OptimizedDeliveryRequest();
        request.setLocations(locationsItemList);
        call = apiInterface.deliveryOptimization(request);
        call.enqueue(new Callback<DeliveryOptimizationResponse>() {
            @Override
            public void onResponse(Call<DeliveryOptimizationResponse> call, Response<DeliveryOptimizationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Intent myIntent = new Intent(DeliveryOptimizationActivity.this, DeliveryOptimizationResultActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("routes",
                                (ArrayList<DeliveryPlanItem>) response.body().getResult().getDeliveryPlan()); //Optional parameters
                        bundle.putParcelableArrayList("locations",
                                (ArrayList<LocationsItem>) locationsItemList); //Optional parameters


                        myIntent.putExtras(bundle);
                        startActivity(myIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryOptimizationResponse> call, Throwable t) {
                call.cancel();

            }
        });
    }

    private void updateLocationsList() {
        locationsAdapter.setLocationsItems(locationsItems);
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
                        LocationsItem locationsItem = new LocationsItem();
                        locationsItem.setLatlon(latlon);
                        locationsItem.setCode(shortCode);
                        locationsItems.add(locationsItem);
                        updateLocationsList();
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
