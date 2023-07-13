package com.eshoppingzone.cartservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eshoppingzone.cartservice.entity.Cart;
import com.eshoppingzone.cartservice.entity.CartResponse;
import com.eshoppingzone.cartservice.exception.UnauthorizedException;
import com.eshoppingzone.cartservice.proxy.ProfileServiceProxy;
import com.eshoppingzone.cartservice.resource.CartResource;
import com.eshoppingzone.cartservice.service.CartService;

@SpringBootTest
class CartResourceTests {

	@Mock
	private CartService cartService;

	@Mock
	private ProfileServiceProxy profileServiceProxy;

	@InjectMocks
	private CartResource cartResource;

	@Test
	void testGetAllCarts() {
		List<Cart> carts = new ArrayList<>();
		carts.add(new Cart());
		carts.add(new Cart());
		when(cartService.getAllCarts()).thenReturn(carts);

		ResponseEntity<List<Cart>> response = cartResource.getAllCarts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(carts, response.getBody());
		verify(cartService, times(1)).getAllCarts();
	}

	@Test
	void testAddCart() {
		Cart cart = new Cart();

		cartResource.addCart(cart);

		verify(cartService, times(1)).addCart(cart);
	}

	@Test
	void testGetCartValidToken() {
		String token = "validToken";
		int cartId = 1;
		Cart cart = new Cart();
		CartResponse cartResponse = new CartResponse();
		doReturn(true).when(profileServiceProxy).validateUserRole(token, "Customer");
		when(cartService.getCartById(cartId)).thenReturn(cartResponse);
		ResponseEntity<CartResponse> expectedResponse = new ResponseEntity<>(cartResponse, HttpStatus.OK);

		ResponseEntity<CartResponse> response = cartResource.getCart(token, cartId);

		assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
		assertEquals(expectedResponse.getBody(), response.getBody());
		verify(profileServiceProxy, times(1)).validateUserRole(token, "Customer");
		verify(cartService, times(1)).getCartById(cartId);
	}

	@Test
	void testGetCartInvalidToken() {
		String token = "invalidToken";
		int cartId = 1;
		doReturn(false).when(profileServiceProxy).validateUserRole(token, "Customer");

		assertThrows(UnauthorizedException.class, () -> cartResource.getCart(token, cartId));
		verify(profileServiceProxy, times(1)).validateUserRole(token, "Customer");
		verify(cartService, never()).getCartById(cartId);
	}

	@Test
	void testUpdateCartValidToken() {
		String token = "validToken";
		Cart cart = new Cart();
		Cart updatedCart = new Cart();
		doReturn(true).when(profileServiceProxy).validateUserRole(token, "Customer");
		when(cartService.updateCart(cart)).thenReturn(updatedCart);
		ResponseEntity<Cart> expectedResponse = new ResponseEntity<>(updatedCart, HttpStatus.OK);

		ResponseEntity<Cart> response = cartResource.updateCart(token, cart);

		assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
		assertEquals(expectedResponse.getBody(), response.getBody());
		verify(profileServiceProxy, times(1)).validateUserRole(token, "Customer");
		verify(cartService, times(1)).updateCart(cart);
	}

	@Test
	void testUpdateCartInvalidToken() {
		String token = "invalidToken";
		Cart cart = new Cart();
		doReturn(false).when(profileServiceProxy).validateUserRole(token, "Customer");

		assertThrows(UnauthorizedException.class, () -> cartResource.updateCart(token, cart));
		verify(profileServiceProxy, times(1)).validateUserRole(token, "Customer");
		verify(cartService, never()).updateCart(cart);
	}

}
