package com.test.myapplication.ui;

import android.content.DialogInterface;
import android.os.Bundle;

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

    private List<DeliveryPlanItem> deliveryPlanItems = new ArrayList<>();
    private List<LocationsItem> locations = new ArrayList<>();
    private RecyclerView rvResults;
    private RouteAdapter routeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_result);
        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        deliveryPlanItems = bundle.getParcelableArrayList("routes");
        locations = bundle.getParcelableArrayList("locations");
        if (deliveryPlanItems != null && locations != null) {
            routeAdapter = new RouteAdapter(getApplicationContext(), deliveryPlanItems, locations);
            rvResults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvResults.setAdapter(routeAdapter);
        }
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
}
