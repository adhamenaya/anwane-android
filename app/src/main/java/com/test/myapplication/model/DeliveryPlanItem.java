package com.test.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryPlanItem{

	@SerializedName("latlon_to")
	private String latlonTo;

	@SerializedName("distance")
	private double distance;

	@SerializedName("latlon_from")
	private String latlonFrom;

	@SerializedName("node_from")
	private int nodeFrom;

	@SerializedName("node_to")
	private int nodeTo;

	public void setLatlonTo(String latlonTo){
		this.latlonTo = latlonTo;
	}

	public String getLatlonTo(){
		return latlonTo;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setLatlonFrom(String latlonFrom){
		this.latlonFrom = latlonFrom;
	}

	public String getLatlonFrom(){
		return latlonFrom;
	}

	public void setNodeFrom(int nodeFrom){
		this.nodeFrom = nodeFrom;
	}

	public int getNodeFrom(){
		return nodeFrom;
	}

	public void setNodeTo(int nodeTo){
		this.nodeTo = nodeTo;
	}

	public int getNodeTo(){
		return nodeTo;
	}

	@Override
 	public String toString(){
		return 
			"DeliveryPlanItem{" + 
			"latlon_to = '" + latlonTo + '\'' + 
			",distance = '" + distance + '\'' + 
			",latlon_from = '" + latlonFrom + '\'' + 
			",node_from = '" + nodeFrom + '\'' + 
			",node_to = '" + nodeTo + '\'' + 
			"}";
		}
}