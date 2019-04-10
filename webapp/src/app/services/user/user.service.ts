import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BASE_URL } from "@config/endpoint";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class UserService {
  endpoint = BASE_URL + "/user";
  constructor(private http: HttpClient) {}

  public getUsers(): Observable<any> {
    return this.http.get(this.endpoint);
  }
}
