import { Injectable } from "@angular/core";
import { Product } from "@models/product";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Category } from "@models/category";
import { BASE_URL } from "@config/endpoint";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  private endpoint = BASE_URL + "/product";
  constructor(private http: HttpClient) {}

  public getProducts(): Observable<Product> {
    return this.http.get<Product>(`${this.endpoint}`);
  }

  public getProduct(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.endpoint}/${id}`);
  }

  public createProduct(category: Category): Observable<Product> {
    return this.http.post<Product>(`${this.endpoint}`, category);
  }

  public updateProduct(category: Category): Observable<Product> {
    return this.http.put<Product>(`${this.endpoint}/${category.id}`, category);
  }

  public deleteProduct(id: number): Observable<Product> {
    return this.http.delete<Product>(`${this.endpoint}/${id}`);
  }
}
