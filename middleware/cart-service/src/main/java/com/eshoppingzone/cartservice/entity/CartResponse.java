package com.eshoppingzone.cartservice.entity;

import java.util.List;

public class CartResponse {
	private int cartId;
	private List<Product> products;
	private double totalPrice;

	public CartResponse() {
		super();
	}

	public CartResponse(int cartId, List<Product> products, double totalPrice) {
		super();
		this.cartId = cartId;
		this.products = products;
		this.totalPrice = totalPrice;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
