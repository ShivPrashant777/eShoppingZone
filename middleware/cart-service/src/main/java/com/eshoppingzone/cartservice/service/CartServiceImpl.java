package com.eshoppingzone.cartservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshoppingzone.cartservice.entity.Cart;
import com.eshoppingzone.cartservice.entity.CartResponse;
import com.eshoppingzone.cartservice.entity.Items;
import com.eshoppingzone.cartservice.entity.Product;
import com.eshoppingzone.cartservice.exception.CartNotFoundException;
import com.eshoppingzone.cartservice.proxy.ProductServiceProxy;
import com.eshoppingzone.cartservice.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	private static Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	private static final String NO_CART_FOUND_MESSAGE = "No Cart Found";

	@Autowired
	CartRepository cartRepository;

	@Autowired
	ProductServiceProxy productServiceProxy;

	@Override
	public CartResponse getCartById(int cartId) {
		Cart dbCart = cartRepository.findById(cartId).orElse(null);
		if (dbCart == null) {
			log.error(NO_CART_FOUND_MESSAGE);
			throw new CartNotFoundException(NO_CART_FOUND_MESSAGE);
		}

		// Populate complete product details by calling product service
		CartResponse cartResponse = new CartResponse(cartId, new ArrayList<>(), dbCart.getTotalPrice());
		for (Items item : dbCart.getItems()) {
			Product productByName = productServiceProxy.getProductById(item.getProductId()).getBody();
			productByName.setQuantity(item.getQuantity());
			cartResponse.getProducts().add(productByName);
		}
		log.info("Cart Retrieved Successfully");
		return cartResponse;
	}

	@Override
	public Cart updateCart(Cart cart) {
		Cart dbCart = cartRepository.findById(cart.getCartId()).orElse(null);
		if (dbCart == null) {
			log.error(NO_CART_FOUND_MESSAGE);
			throw new CartNotFoundException(NO_CART_FOUND_MESSAGE);
		}
		if (cart.getItems() != null) {
			dbCart.setItems(cart.getItems());
		}
		double totalPrice = cartTotal(dbCart);
		dbCart.setTotalPrice(totalPrice);
		log.info("Cart Updated Successfully");
		return cartRepository.save(dbCart);
	}

	@Override
	public List<Cart> getAllCarts() {
		log.info("Retrieved All Carts Successfully");
		return cartRepository.findAll();
	}

	@Override
	public double cartTotal(Cart cart) {
		double totalPrice = 0;
		for (Items item : cart.getItems()) {
			totalPrice += item.getQuantity() * item.getPrice();
		}
		return totalPrice;
	}

	@Override
	public Cart addCart(Cart cart) {
		Cart dbCart = cartRepository.findById(cart.getCartId()).orElse(null);
		if (dbCart != null) {
			return new Cart(cart.getCartId(), cart.getItems());
		}
		double totalPrice = cartTotal(cart);
		cart.setTotalPrice(totalPrice);
		log.info("Cart Added Successfully");
		return cartRepository.save(cart);
	}

}
