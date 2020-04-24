package io.enwane.app.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("delivery_plan")
	private List<DeliveryPlanItem> deliveryPlan;

	@SerializedName("total_distance")
	private double totalDistance;

	public void setDeliveryPlan(List<DeliveryPlanItem> deliveryPlan){
		this.deliveryPlan = deliveryPlan;
	}

	public List<DeliveryPlanItem> getDeliveryPlan(){
		return deliveryPlan;
	}

	public void setTotalDistance(double totalDistance){
		this.totalDistance = totalDistance;
	}

	public double getTotalDistance(){
		return totalDistance;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"delivery_plan = '" + deliveryPlan + '\'' + 
			",total_distance = '" + totalDistance + '\'' + 
			"}";
		}
}