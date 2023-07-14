import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderRequest } from '../models/OrderRequest.model';
import { AppConstants } from '../constants/app.constants';

const { API_GATEWAY_URL, ORDER_SERVICE_URL } = AppConstants;

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private httpClient: HttpClient) {}

  public getAllOrdersByCustomerId(customerId: number) {
    return this.httpClient.get(
      `${API_GATEWAY_URL}/${ORDER_SERVICE_URL}/getAllOrdersByCustomerId/${customerId}`
    );
  }

  public createTransaction(amount: number) {
    return this.httpClient.get(
      `${API_GATEWAY_URL}/${ORDER_SERVICE_URL}/createTransaction/${amount}`
    );
  }

  public placeOrder(orderRequest: OrderRequest) {
    return this.httpClient.post(
      `${API_GATEWAY_URL}/${ORDER_SERVICE_URL}/placeOrder`,
      orderRequest
    );
  }

  public getUserAddressesByProfileId(profileId: number) {
    return this.httpClient.get(
      `${API_GATEWAY_URL}/${ORDER_SERVICE_URL}/getUserAddressesByProfileId/${profileId}`
    );
  }
}
