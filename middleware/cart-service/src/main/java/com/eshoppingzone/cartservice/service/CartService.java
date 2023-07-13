package com.eshoppingzone.cartservice.service;

import java.util.List;

import com.eshoppingzone.cartservice.entity.Cart;
import com.eshoppingzone.cartservice.entity.CartResponse;

public interface CartService {
	public CartResponse getCartById(int cartId);

	public Cart updateCart(Cart cart);

	public List<Cart> getAllCarts();

	public double cartTotal(Cart cart);

	public Cart addCart(Cart cart);
}
