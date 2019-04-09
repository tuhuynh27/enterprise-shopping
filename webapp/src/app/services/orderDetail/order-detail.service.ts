import { Injectable } from "@angular/core";
import { BASE_URL } from "@config/endpoint";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class OrderDetailService {
  private endpoint = `${BASE_URL}/order_detail`;
  constructor(private http: HttpClient) {}

  public createOrderDetail(
    orderId: number,
    productId: number,
    quantity: number
  ): Observable<any> {
    return this.http.post(`${this.endpoint}`, {
      orderDetailIdentify: {
        order: {
          id: orderId
        },
        product: {
          id: productId
        }
      },
      discount: 0,
      quantity
    });
  }
}
