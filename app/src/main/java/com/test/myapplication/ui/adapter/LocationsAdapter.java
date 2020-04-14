package com.test.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.myapplication.R;
import com.test.myapplication.model.LocationsItem;
import com.test.myapplication.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LocationsItem> locationsItems;
    Context context;
    DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);


    public LocationsAdapter(Context context, List<LocationsItem> locationsItems) {
        this.locationsItems = locationsItems;
        this.context = context;
    }

    public void setLocationsItems(List<LocationsItem> locationsItems) {
        this.locationsItems = locationsItems;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof LocationViewHolder) {
            LocationViewHolder holder = (LocationViewHolder) viewHolder;
            final LocationsItem locationsItem = locationsItems.get(position);
            String[] location = locationsItem.getLatlon().split(",");
            double latitude = Double.parseDouble(location[0]);
            double longitude = Double.parseDouble(location[1]);
            String formattedLocationToDisplay = decimalFormat.format(latitude) + "," + decimalFormat.format(longitude);
            holder.tvLocation.setText(formattedLocationToDisplay);
        }
    }

    @Override
    public int getItemCount() {
        return locationsItems.size();
    }


    public class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_location)
        TextView tvLocation;

        public LocationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

