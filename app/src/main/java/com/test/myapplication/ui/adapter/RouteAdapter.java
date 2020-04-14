package com.test.myapplication.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.myapplication.R;
import com.test.myapplication.model.DeliveryPlanItem;
import com.test.myapplication.model.LocationsItem;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RouteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<DeliveryPlanItem> deliveryPlanItems;
    List<LocationsItem> locations;
    Context context;

    public RouteAdapter(Context context, List<DeliveryPlanItem> deliveryPlanItems, List<LocationsItem> locations) {
        this.deliveryPlanItems = deliveryPlanItems;
        this.locations = locations;
        this.context = context;
    }

    public void setDeliveryPlanItems(List<DeliveryPlanItem> deliveryPlanItems) {
        this.deliveryPlanItems = deliveryPlanItems;
        this.locations = locations;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof RouteViewHolder) {
            RouteViewHolder holder = (RouteViewHolder) viewHolder;
            final DeliveryPlanItem deliveryPlanItem = deliveryPlanItems.get(position);

            holder.tvFromIndex.setText(deliveryPlanItem.getNodeFrom() + "");
            holder.tvFromCode.setText(locations.get(deliveryPlanItem.getNodeFrom()).getCode());
            holder.tvToIndex.setText(deliveryPlanItem.getNodeTo() + "");
            holder.tvToCode.setText(locations.get(deliveryPlanItem.getNodeTo()).getCode());
            holder.tvDistance.setText(deliveryPlanItem.getDistance() + "");
        }
    }

    @Override
    public int getItemCount() {
        return deliveryPlanItems.size();
    }


    public class RouteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFromIndex)
        TextView tvFromIndex;
        @BindView(R.id.tvFromCode)
        TextView tvFromCode;
        @BindView(R.id.tvToIndex)
        TextView tvToIndex;
        @BindView(R.id.tvToCode)
        TextView tvToCode;
        @BindView(R.id.tvDistance)
        TextView tvDistance;
        @BindView(R.id.btnRoute)
        Button btnRoute;

        public RouteViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
