import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Category } from "@models/category";

@Injectable({
  providedIn: "root"
})
export class CategoryService {
  private endpoint = "http://localhost:3000/api";
  constructor(private http: HttpClient) {}

  public getCategories(): Observable<Category> {
    return this.http.get<Category>(`${this.endpoint}/category/`);
  }

  public getCategory(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.endpoint}/category/${id}`);
  }

  public createCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.endpoint}/category/`, category);
  }

  public updateCategory(category: Category): Observable<Category> {
    return this.http.put<Category>(
      `${this.endpoint}/category/${category.id}`,
      category
    );
  }

  public deleteCategory(id: number): Observable<Category> {
    return this.http.delete<Category>(`${this.endpoint}/category/${id}`);
  }
}
