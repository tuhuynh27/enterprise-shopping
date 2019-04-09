import { Injectable } from "@angular/core";
import { BASE_URL } from "@config/endpoint";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root"
})
export class OrderService {
  private endpoint = `${BASE_URL}/order`;
  constructor(private http: HttpClient) {}

  public getOrders() {
    return this.http.get(`${this.endpoint}`);
  }

  public getOrderDetails(id: any) {
    return this.http.get(`${this.endpoint}/${id}/view`);
  }

  public markShipped(id: any) {
    return this.http.post(`${this.endpoint}/${id}/shipped`, {});
  }
}
