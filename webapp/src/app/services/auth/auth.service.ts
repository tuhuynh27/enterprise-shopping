import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BASE_URL } from "@config/endpoint";
import { User } from "@models/user";
import { Subject, BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private endpoint = BASE_URL + "/auth";

  isLogged: Subject<any> = new BehaviorSubject(false);

  constructor(private http: HttpClient) {}

  public login(credentials: User) {
    return this.http.post<any>(`${this.endpoint}/login`, credentials);
  }

  public loginSuccess() {
    this.isLogged.next(true);
  }

  public signup(credentials: User) {
    return this.http.post<User>(`${this.endpoint}/new`, credentials);
  }
}
