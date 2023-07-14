import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Product } from '../models/Product.model';
import { AppConstants } from '../constants/app.constants';

const { API_GATEWAY_URL, PRODUCT_SERVICE_URL } = AppConstants;

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  public products: Product[] = [];
  constructor(private http: HttpClient) {}

  public productsUpdated = new EventEmitter<Product[]>();

  public getAllProducts() {
    return this.http.get(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/getAllProducts`,
      { observe: 'response' }
    );
  }

  public getProductById(productId: any) {
    return this.http.get(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/getProductById/${productId}`,
      { observe: 'response' }
    );
  }

  public searchProducts(productName: string) {
    console.log('Search Called');
    if (productName === '') {
      return this.getAllProducts();
    }
    return this.http.get(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/searchProducts/${productName}`,
      { observe: 'response' }
    );
  }

  public sortByName() {
    return this.http.get(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/sortByName`,
      { observe: 'response' }
    );
  }

  public sortByPrice() {
    return this.http.get(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/sortByPrice`,
      { observe: 'response' }
    );
  }
  public addProduct(product: Product) {
    return this.http.post(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/addProducts`,
      product,
      { observe: 'response' }
    );
  }

  public updateProduct(product: Product) {
    return this.http.put(
      `${API_GATEWAY_URL}/${PRODUCT_SERVICE_URL}/updateProducts`,
      product,
      { observe: 'response' }
    );
  }

  public getAverageRating(product: Product) {
    let avgRating = 0;
    let count = 0;
    Object.entries(product.rating).map((entry) => {
      let value = entry[1];
      avgRating += value;
      count++;
    });
    avgRating = count > 0 ? Math.ceil((avgRating /= count)) : avgRating;
    return avgRating;
  }
}
