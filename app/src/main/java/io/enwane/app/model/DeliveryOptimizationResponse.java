package io.enwane.app.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryOptimizationResponse{

	@SerializedName("result")
	private Result result;

	@SerializedName("success")
	private boolean success;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
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
			"DeliveryOptimizationResponse{" + 
			"result = '" + result + '\'' + 
			",success = '" + success + '\'' + 
			"}";
		}
}