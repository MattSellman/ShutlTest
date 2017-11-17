package com.shutl.model;

public class Item {
	private int weight;
	private int width;
	private int height;
	private int length;

	//constructor
	public Item() {
	}

	//variables for vehicle - invalid inputs if zero
	public Item(int weight, int width, int height, int length) {

		this.weight = weight;
		this.width = width;
		this.height = height;
		this.length = length;
	}

	//public getters and setters for access to private variables
	//getter and setter for weight
	public int getWeight() {
		return weight;
	}

	void setWeight(int weight) {
		this.weight = weight;
	}

	//getter and setter for width
	public int getWidth() {
		return width;
	}

	void setWidth(int width) {
		this.width = width;
	}

	//getter and setter for height
	public int getHeight() {
		return height;
	}

	void setHeight(int height) {
		this.height = height;
	}

	//getter and setter for length
	public int getLength() {
		return length;
	}

	void setLength(int length) {
		this.length = length;
	}
}