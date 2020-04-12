package com.test.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ShortAddressResponse{

	@SerializedName("address")
	private Address address;

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	@Override
 	public String toString(){
		return 
			"ShortAddressResponse{" + 
			"address = '" + address + '\'' + 
			"}";
		}
}