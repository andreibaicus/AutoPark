package com.model;


public class Vehicle {

	private int id;
	private String brand;
	private int horsePower;
	private String vin;
	private String color;
	private boolean automatic;
	
	public Vehicle() {
		
	}

	public Vehicle(String brand, int id, int horsePower, String color,
			boolean automatic) {
		super();
		this.id = id;
		this.brand = brand;
		this.horsePower = horsePower;
		this.color = color;
		this.automatic = automatic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	protected String returnBrandWithVin(){
		StringBuilder builder = new StringBuilder();
		builder.append(this.brand).append(" ").append(this.vin);
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("id= ").append(this.id);
		strBuilder.append(" brand = ").append(this.brand);	
		strBuilder.append(", horsePower = ").append(this.horsePower);
		strBuilder.append(", vin = ").append(this.vin);
		strBuilder.append(", color = ").append(this.color);
		strBuilder.append(", automatic = ").append(this.automatic);
		
		return strBuilder.toString();
	}

		
}
