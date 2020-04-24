package io.enwane.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.enwane.R;
import io.enwane.app.model.LocationsItem;
import io.enwane.app.utils.Constants;

import java.text.DecimalFormat;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<LocationsItem> locationsItems;
    Context context;
    DecimalFormat decimalFormat = new DecimalFormat(Constants.DECIMAL_FORMAT);
    OnLocationCallback onLocationCallback;


    public LocationsAdapter(Context context, List<LocationsItem> locationsItems) {
        this.locationsItems = locationsItems;
        this.context = context;
    }

    public void setOnLocationCallback(OnLocationCallback onLocationCallback) {
        this.onLocationCallback = onLocationCallback;
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
            holder.tvCode.setText(locationsItem.getCode());
            holder.tvIndex.setText(String.valueOf(position + 1));
            holder.btnLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLocationCallback.onLocationDeleted(locationsItem);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return locationsItems.size();
    }


    public interface OnLocationCallback {
        void onLocationDeleted(LocationsItem locationsItem);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.btn_delete_location)
        Button btnLocation;
        @BindView(R.id.tvIndex)
        TextView tvIndex;

        public LocationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

