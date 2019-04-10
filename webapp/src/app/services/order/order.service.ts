import { Injectable } from "@angular/core";
import { BASE_URL } from "@config/endpoint";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class OrderService {
  private endpoint = `${BASE_URL}/order`;
  constructor(private http: HttpClient) {}

  public getOrders() {
    return this.http.get(`${this.endpoint}`);
  }

  public getMyOrders() {
    return this.http.get(`${this.endpoint}/my`);
  }

  public getOrderDetails(id: any) {
    return this.http.get(`${this.endpoint}/${id}/view`);
  }

  public markShipped(id: any) {
    return this.http.post(`${this.endpoint}/${id}/shipped`, {});
  }

  public createOrder(userId: number, userName: string): Observable<any> {
    return this.http.post(`${this.endpoint}`, {
      description: `Order of ${userName}`,
      userId
    });
  }
}
