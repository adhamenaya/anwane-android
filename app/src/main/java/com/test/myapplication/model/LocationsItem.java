package com.test.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class LocationsItem{

	@SerializedName("latlon")
	private String latlon;

	public void setLatlon(String latlon){
		this.latlon = latlon;
	}

	public String getLatlon(){
		return latlon;
	}

	@Override
 	public String toString(){
		return 
			"LocationsItem{" + 
			"latlon = '" + latlon + '\'' + 
			"}";
		}
}