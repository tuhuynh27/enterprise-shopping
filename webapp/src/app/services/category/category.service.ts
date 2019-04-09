import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, Subject, BehaviorSubject } from "rxjs";
import { Category } from "@models/category";
import { BASE_URL } from "@config/endpoint";

@Injectable({
  providedIn: "root"
})
export class CategoryService {
  private endpoint = BASE_URL + "/category";
  listCategories: Subject<Category[]> = new BehaviorSubject([]);
  constructor(private http: HttpClient) {}

  public setListCategories(data: Category[]) {
    this.listCategories.next(data);
  }

  public getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.endpoint}`);
  }

  public getCategory(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.endpoint}/${id}`);
  }

  public createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.endpoint}`, category);
  }

  public updateCategory(category: Category): Observable<Category> {
    return this.http.put<Category>(`${this.endpoint}/${category.id}`, category);
  }

  public deleteCategory(id: number): Observable<Category> {
    return this.http.delete<Category>(`${this.endpoint}/${id}`);
  }
}
