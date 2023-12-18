package com.marolix.project.model;

public class Hotel {
	private int id;
	private String productName;
	private double productPrice;
	private int quantity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Hotel(int id, String productName, double productPrice, int quantity) {
		super();
		this.id = id;
		this.productName = productName;
		this.productPrice = productPrice;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Hotel [id=" + id + ", productName=" + productName + ", productPrice=" + productPrice + ", quantity="
				+ quantity + "]";
	}


}
