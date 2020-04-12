package com.test.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("latlon")
	private String latlon;

	@SerializedName("short_code")
	private String shortCode;

	public void setLatlon(String latlon){
		this.latlon = latlon;
	}

	public String getLatlon(){
		return latlon;
	}

	@Override
	public String toString() {
		return "Address{" +
				"latlon='" + latlon + '\'' +
				", shortCode='" + shortCode + '\'' +
				'}';
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
}