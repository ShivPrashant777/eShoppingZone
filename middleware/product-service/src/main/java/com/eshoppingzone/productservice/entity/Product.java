package com.eshoppingzone.productservice.entity;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Product {

	@Transient
	public static final String SEQUENCE_NAME = "product_sequence";

	@Id
	private int productId;
	@NotBlank(message = "Product Type should not be empty")
	private String productType;
	@NotBlank(message = "Product Name should not be empty")
	private String productName;
	@NotBlank(message = "Category Type should not be empty")
	private String category;
	private Map<Integer, Double> rating;
	private Map<Integer, String> review;
	private String image;
	@Min(value = (long) 0.1, message = "Provide a valid price")
	private double price;
	@NotBlank(message = "Provide a description to the product")
	private String description;
	private Map<String, String> specification;

	public Product() {
		super();
	}

	public Product(int productId, String productType, String productName, String category, Map<Integer, Double> rating,
			Map<Integer, String> review, String image, double price, String description,
			Map<String, String> specification) {
		super();
		this.productId = productId;
		this.productType = productType;
		this.productName = productName;
		this.category = category;
		this.rating = rating;
		this.review = review;
		this.image = image;
		this.price = price;
		this.description = description;
		this.specification = specification;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Map<Integer, Double> getRating() {
		return rating;
	}

	public void setRating(Map<Integer, Double> rating) {
		this.rating = rating;
	}

	public Map<Integer, String> getReview() {
		return review;
	}

	public void setReview(Map<Integer, String> review) {
		this.review = review;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getSpecification() {
		return specification;
	}

	public void setSpecification(Map<String, String> specification) {
		this.specification = specification;
	}

}
