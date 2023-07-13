package com.eshoppingzone.cartservice.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Cart {

	@Id
	private int cartId; // Cart id will be assigned by the customer id
	private List<Items> items;
	private double totalPrice;

	public Cart() {
		super();
	}

	public Cart(int cartId, List<Items> items) {
		super();
		this.cartId = cartId;
		this.items = items;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
