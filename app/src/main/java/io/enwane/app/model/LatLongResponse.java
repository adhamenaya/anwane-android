package io.enwane.app.model;

import com.google.gson.annotations.SerializedName;

public class LatLongResponse{

	@SerializedName("address")
	private Address address;

	@SerializedName("success")
	private boolean success;

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
 	public String toString(){
		return 
			"LatLongResponse{" + 
			"address = '" + address + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}