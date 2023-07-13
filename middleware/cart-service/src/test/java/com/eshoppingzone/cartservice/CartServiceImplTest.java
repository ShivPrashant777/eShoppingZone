package com.eshoppingzone.cartservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eshoppingzone.cartservice.entity.Cart;
import com.eshoppingzone.cartservice.entity.CartResponse;
import com.eshoppingzone.cartservice.entity.Items;
import com.eshoppingzone.cartservice.entity.Product;
import com.eshoppingzone.cartservice.exception.CartNotFoundException;
import com.eshoppingzone.cartservice.proxy.ProductServiceProxy;
import com.eshoppingzone.cartservice.repository.CartRepository;
import com.eshoppingzone.cartservice.service.CartServiceImpl;

@SpringBootTest
class CartServiceImplTest {

	@Mock
	private CartRepository cartRepository;

	@Mock
	private ProductServiceProxy productServiceProxy;

	@InjectMocks
	private CartServiceImpl cartService;

	@Test
	void testGetCartByIdCartExists() {
		int cartId = 1;
		Cart dbCart = new Cart();
		dbCart.setCartId(cartId);
		List<Items> items = new ArrayList<>();
		items.add(new Items(1, 25, 2));
		items.add(new Items(2, 100, 5));
		dbCart.setItems(items);
		dbCart.setTotalPrice(100.0);
		Product product1 = new Product();
		product1.setProductName("Name1");
		product1.setProductId(1);
		Product product2 = new Product();
		product2.setProductName("Name2");
		product2.setProductId(2);
		doReturn(Optional.of(dbCart)).when(cartRepository).findById(cartId);
		doReturn(new ResponseEntity<>(product1, HttpStatus.OK)).when(productServiceProxy).getProductById(1);
		doReturn(new ResponseEntity<>(product2, HttpStatus.OK)).when(productServiceProxy).getProductById(2);

		CartResponse cartResponse = cartService.getCartById(cartId);

		assertNotNull(cartResponse);
		assertEquals(cartId, cartResponse.getCartId());
		assertEquals(2, cartResponse.getProducts().size());
		assertEquals(100.0, cartResponse.getTotalPrice());
		assertEquals(product1, cartResponse.getProducts().get(0));
		assertEquals(product2, cartResponse.getProducts().get(1));
		verify(cartRepository, times(1)).findById(cartId);
		verify(productServiceProxy, times(2)).getProductById(anyInt());
	}

	@Test
	void testGetCartByIdCartDoesNotExist() {
		int cartId = 1;
		doReturn(Optional.empty()).when(cartRepository).findById(cartId);

		assertThrows(CartNotFoundException.class, () -> cartService.getCartById(cartId));
		verify(cartRepository, times(1)).findById(cartId);
		verify(productServiceProxy, never()).getProductById(anyInt());
	}

	@Test
	void testUpdateCartCartExists() {
		Cart inputCart = new Cart();
		int cartId = 1;
		inputCart.setCartId(cartId);
		List<Items> items = new ArrayList<>();
		items.add(new Items(1, 25, 2));
		items.add(new Items(2, 100, 5));
		inputCart.setItems(items);

		Cart dbCart = new Cart();
		dbCart.setCartId(cartId);
		List<Items> initialItems = new ArrayList<>();
		initialItems.add(new Items(3, 500, 5));
		dbCart.setItems(initialItems);
		double initialTotalPrice = 625.0;
		dbCart.setTotalPrice(initialTotalPrice);

		double expectedTotalPrice = cartService.cartTotal(inputCart);
		doReturn(Optional.of(dbCart)).when(cartRepository).findById(cartId);
		doReturn(dbCart).when(cartRepository).save(dbCart);

		Cart updatedCart = cartService.updateCart(inputCart);

		assertNotNull(updatedCart);
		assertEquals(cartId, updatedCart.getCartId());
		assertEquals(items, updatedCart.getItems());
		assertEquals(expectedTotalPrice, updatedCart.getTotalPrice());
		verify(cartRepository, times(1)).findById(cartId);
		verify(cartRepository, times(1)).save(dbCart);
	}

	@Test
	void testUpdateCartCartDoesNotExist() {
		Cart inputCart = new Cart();
		int cartId = 1;
		inputCart.setCartId(cartId);
		List<Items> items = new ArrayList<>();
		items.add(new Items(1, 25, 2));
		items.add(new Items(2, 100, 5));
		inputCart.setItems(items);

		doReturn(Optional.empty()).when(cartRepository).findById(cartId);

		assertThrows(CartNotFoundException.class, () -> cartService.updateCart(inputCart));
		verify(cartRepository, times(1)).findById(cartId);
		verify(cartRepository, never()).save(any());
	}

	@Test
	void testGetAllCarts() {
		List<Cart> expectedCarts = new ArrayList<>();
		expectedCarts.add(new Cart());
		expectedCarts.add(new Cart());
		doReturn(expectedCarts).when(cartRepository).findAll();

		List<Cart> carts = cartService.getAllCarts();

		assertNotNull(carts);
		assertEquals(expectedCarts, carts);
		verify(cartRepository, times(1)).findAll();
	}

	@Test
	void testAddCartCartDoesNotExist() {
		Cart inputCart = new Cart();
		int cartId = 1;
		inputCart.setCartId(cartId);
		List<Items> items = new ArrayList<>();
		items.add(new Items(1, 25, 2));
		items.add(new Items(2, 100, 5));
		inputCart.setItems(items);

		Cart dbCart = null;
		double expectedTotalPrice = cartService.cartTotal(inputCart);
		doReturn(Optional.ofNullable(dbCart)).when(cartRepository).findById(cartId);
		doReturn(inputCart).when(cartRepository).save(inputCart);

		Cart addedCart = cartService.addCart(inputCart);

		assertNotNull(addedCart);
		assertEquals(inputCart, addedCart);
		assertEquals(expectedTotalPrice, addedCart.getTotalPrice());
		verify(cartRepository, times(1)).findById(cartId);
		verify(cartRepository, times(1)).save(inputCart);
	}

	@Test
	void testAddCartCartExists() {
		Cart inputCart = new Cart();
		int cartId = 1;
		inputCart.setCartId(cartId);
		List<Items> items = new ArrayList<>();
		items.add(new Items(1, 25, 2));
		items.add(new Items(2, 100, 5));
		inputCart.setItems(items);

		Cart dbCart = new Cart();
		dbCart.setCartId(cartId);
		doReturn(Optional.of(dbCart)).when(cartRepository).findById(cartId);

		Cart addedCart = cartService.addCart(inputCart);

		assertNotNull(addedCart);
	}
}
