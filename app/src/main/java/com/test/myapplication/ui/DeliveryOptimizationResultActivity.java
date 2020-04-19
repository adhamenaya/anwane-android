package com.test.myapplication.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.test.myapplication.R;
import com.test.myapplication.model.DeliveryPlanItem;
import com.test.myapplication.model.LocationsItem;
import com.test.myapplication.ui.adapter.RouteAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryOptimizationResultActivity extends AppCompatActivity {

    // current location
    String currLocationLatlon = "";
    String currLocationCode = "";
    private List<DeliveryPlanItem> deliveryPlanItems = new ArrayList<>();
    private List<LocationsItem> locations = new ArrayList<>();
    private RecyclerView rvResults;
    private RouteAdapter routeAdapter;
    private Button btnShowAllRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_result);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        btnShowAllRoute = (Button) findViewById(R.id.btnShowAllRoute);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        deliveryPlanItems = bundle.getParcelableArrayList("routes");
        locations = bundle.getParcelableArrayList("locations");
        if (deliveryPlanItems != null && locations != null) {
            routeAdapter = new RouteAdapter(getApplicationContext(), deliveryPlanItems, locations);
            rvResults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvResults.setAdapter(routeAdapter);
        }
        try {
            currLocationCode = getIntent().getExtras().getString("currLocationCode");
            currLocationLatlon = getIntent().getExtras().getString("currLocationLatlon");
        } catch (NullPointerException ne) {
        }
        btnShowAllRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startLatlon = deliveryPlanItems.get(0).getLatlonFrom();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + startLatlon +
                                "&daddr=" + buildAllDestinations()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("هل تريد فعلاً الخروج؟")
                .setCancelable(false)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeliveryOptimizationResultActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("لا", null)
                .show();

    }

    private String buildAllDestinations() {
        String dests = "";
        if (deliveryPlanItems != null) {
            for (int i = 0; i < deliveryPlanItems.size(); i++) {
                if (i == 0)
                    dests += deliveryPlanItems.get(i).getLatlonFrom();
                else if (i == deliveryPlanItems.size() - 1) {
                    dests += "+to:" + deliveryPlanItems.get(i).getLatlonFrom();
                    dests += "+to:" + deliveryPlanItems.get(i).getLatlonTo();

                } else {
                    dests += "+to:" + deliveryPlanItems.get(i).getLatlonFrom();
                }
            }
        }
        return dests;
    }
}
