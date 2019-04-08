import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Supplier } from "@models/supplier";

@Injectable({
  providedIn: "root"
})
export class SupplierService {
  private endpoint = "http://localhost:3000/api/supplier";
  constructor(private http: HttpClient) {}

  public getSuppliers(): Observable<Supplier> {
    return this.http.get<Supplier>(`${this.endpoint}`);
  }

  public getSupplier(id: number): Observable<Supplier> {
    return this.http.get<Supplier>(`${this.endpoint}/${id}`);
  }

  public createSupplier(supplier: Supplier): Observable<Supplier> {
    return this.http.post<Supplier>(`${this.endpoint}`, supplier);
  }

  public updateSupplier(supplier: Supplier): Observable<Supplier> {
    return this.http.put<Supplier>(`${this.endpoint}/${supplier.id}`, supplier);
  }

  public deleteSupplier(id: number): Observable<Supplier> {
    return this.http.delete<Supplier>(`${this.endpoint}/${id}`);
  }
}
