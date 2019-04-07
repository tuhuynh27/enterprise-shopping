import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class FakeService {
  private endpoint = "https://jsonplaceholder.typicode.com";
  constructor(private http: HttpClient) {}

  public getPhotos(): Observable<any> {
    return this.http.get<any>(`${this.endpoint}/photos?_page=1&_limit=10`);
  }
}
