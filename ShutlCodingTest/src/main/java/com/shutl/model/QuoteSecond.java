package com.shutl.model;

import java.util.List;

public class QuoteSecond {
	private String pickupPostcode;
	private String deliveryPostcode;
	private Long price;
	private static String vehicle_id;
	
	//list holding items (see Items class)
	private List<Item> Items;

	//constructor
	public QuoteSecond() {
	}

	//first quote for postcode - copied from Quote class
	public QuoteSecond(String pickupPostcode, String deliveryPostcode) {

		this.pickupPostcode = pickupPostcode;
		this.deliveryPostcode = deliveryPostcode;
	}

	//second quote for price - copied from Quote class
	public QuoteSecond(String pickupPostcode, String deliveryPostcode, Long price) {

		//getters and setters for postcode + price
		this.pickupPostcode = pickupPostcode;
		this.deliveryPostcode = deliveryPostcode;
		this.price = price;	
	}

	public QuoteSecond(String pickupPostcode, String deliveryPostcode, String vehicle_id) {
		this.pickupPostcode = pickupPostcode;
		this.deliveryPostcode = deliveryPostcode;
		QuoteSecond.vehicle_id = vehicle_id;
	}

	public QuoteSecond(String pickupPostcode, String deliveryPostcode, Long price, String vehicle_id) {

		this.pickupPostcode = pickupPostcode;
		this.deliveryPostcode = deliveryPostcode;
		this.price = price;
		QuoteSecond.vehicle_id = vehicle_id;
		
		//for the adding of percentage price increases to price variable
		if(QuoteSecond.vehicle_id == "bicycle") {
			long markup1 = (long) (price * 0.10);
			this.price += markup1;
			
		} else if(QuoteSecond.vehicle_id == "motorbike") {
			long markup2 = (long) (price * 0.15);
			this.price += markup2;
			
		} else if(QuoteSecond.vehicle_id == "parcel_van") {
			long markup3 = (long) (price * 0.20);
			this.price += markup3;
			
		} else if(QuoteSecond.vehicle_id == "small_van") {
			long markup4 = (long) (price * 0.30);
			this.price += markup4;
			
		} else if(QuoteSecond.vehicle_id == "large_van") {
			long markup5 = (long) (price * 0.40);
			this.price += markup5;
		}
		
		
		
		
		if(QuoteSecond.vehicle_id == "bicycle" && price > 500) {
			price = (long) 500;
		} else if(QuoteSecond.vehicle_id == "motorbike" && price > 750) {
			price = (long) 750;
		} else if(QuoteSecond.vehicle_id == "parcel_van" && price > 1000) {
			price = (long) 1000;
		} else if(QuoteSecond.vehicle_id == "small_van" && price > 1500) {
			price = (long) 1500;
		} 
		//large van has no limit, therefore no price limit needed
	}

	public QuoteSecond(String pickupPostcode, String deliveryPostcode, List<Item> Items) {
		//to determine the size on an unknown vehicle
		
		this.pickupPostcode = pickupPostcode;
		this.deliveryPostcode = deliveryPostcode;
		this.Items = Items;
	}

	//getters and setters for delivery
	public String getPickupPostcode() {
		return pickupPostcode;
	}

	void setPickupPostcode(String pickupPostcode) {
		this.pickupPostcode = pickupPostcode;
	}

	public String getDeliveryPostcode() {
		return deliveryPostcode;
	}

	public void setDeliveryPostcode(String deliveryPostcode) {
		this.deliveryPostcode = deliveryPostcode;
	}

	//getter and setter for price
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	//getter and setter for vehicle
	public String getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(String vehicle_id) {
		QuoteSecond.vehicle_id = vehicle_id;
	}

	//getters and setters for Item class
	public List<Item> getItems() {
		return Items;
	}

	public void setItems(List<Item> Items) {
		this.Items = Items;
	}
}
