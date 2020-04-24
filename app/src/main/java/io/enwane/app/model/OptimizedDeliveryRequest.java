package io.enwane.app.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OptimizedDeliveryRequest{

	@SerializedName("locations")
	private List<LocationsItem> locations;

	public void setLocations(List<LocationsItem> locations){
		this.locations = locations;
	}

	public List<LocationsItem> getLocations(){
		return locations;
	}

	@Override
 	public String toString(){
		return 
			"OptimizedDeliveryRequest{" + 
			"locations = '" + locations + '\'' + 
			"}";
		}
}