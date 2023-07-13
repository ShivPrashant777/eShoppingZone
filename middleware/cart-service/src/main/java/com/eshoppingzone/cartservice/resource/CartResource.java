package com.eshoppingzone.cartservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingzone.cartservice.entity.Cart;
import com.eshoppingzone.cartservice.entity.CartResponse;
import com.eshoppingzone.cartservice.exception.UnauthorizedException;
import com.eshoppingzone.cartservice.proxy.ProfileServiceProxy;
import com.eshoppingzone.cartservice.service.CartService;

@RestController
@RequestMapping("/cart-service/carts")
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT }, origins = "*")
public class CartResource {

	private static final String ACCESS_DENIED_MESSAGE = "Access Denied";

	@Autowired
	private CartService cartService;

	@Autowired
	private ProfileServiceProxy profileServiceProxy;

	/**
	 * 
	 * @access Open
	 * @description Get All Carts
	 */
	@GetMapping("/getAllCarts")
	public ResponseEntity<List<Cart>> getAllCarts() {
		return new ResponseEntity<>(cartService.getAllCarts(), HttpStatus.OK);
	}

	/**
	 * 
	 * @param cart
	 * @access Authenticated(Any)
	 * @description Instantiate the Cart
	 */
	@PostMapping("/addCart")
	public void addCart(@RequestBody final Cart cart) {

		cartService.addCart(cart);
	}

	/**
	 * 
	 * @param cartId
	 * @access Authenticated(Customer)
	 * @description Get Cart From Cart(User) Id
	 */
	@GetMapping("/getCart/{cartId}")
	public ResponseEntity<CartResponse> getCart(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@PathVariable final int cartId) {
		if (!profileServiceProxy.validateUserRole(token, "Customer")) {
			throw new UnauthorizedException(ACCESS_DENIED_MESSAGE);
		}
		return new ResponseEntity<>(cartService.getCartById(cartId), HttpStatus.OK);
	}

	/**
	 * 
	 * @param cart
	 * @access Authenticated(Customer)
	 * @description Update Cart
	 */
	@PutMapping("/updateCart")
	public ResponseEntity<Cart> updateCart(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token,
			@RequestBody final Cart cart) {
		if (!profileServiceProxy.validateUserRole(token, "Customer")) {
			throw new UnauthorizedException(ACCESS_DENIED_MESSAGE);
		}
		return new ResponseEntity<>(cartService.updateCart(cart), HttpStatus.OK);
	}
}
