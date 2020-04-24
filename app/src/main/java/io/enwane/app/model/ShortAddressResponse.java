package io.enwane.app.model;

import com.google.gson.annotations.SerializedName;

public class ShortAddressResponse{

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

	@Override
	public String toString() {
		return "ShortAddressResponse{" +
				"address=" + address +
				", success=" + success +
				'}';
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}