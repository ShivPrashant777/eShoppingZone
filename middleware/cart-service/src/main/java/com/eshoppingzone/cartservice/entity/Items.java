package com.eshoppingzone.cartservice.entity;

public class Items {
	private int productId;
	private double price;
	private int quantity;

	public Items() {
		super();
	}

	public Items(int productId, double price, int quantity) {
		super();
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
