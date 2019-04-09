import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BASE_URL } from "@config/endpoint";
import { User } from "@models/user";
import { Subject, BehaviorSubject } from "rxjs";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: "root"
})
export class AuthService {
  private endpoint = BASE_URL + "/auth";

  isLogged: Subject<any> = new BehaviorSubject(
    localStorage.getItem("access_token") ? true : false
  );
  private tokenDecode = this.jwtHelper.decodeToken(
    localStorage.getItem("access_token")
  );
  name: Subject<any> = new BehaviorSubject(
    (this.tokenDecode && this.tokenDecode.sub) || ""
  );

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) {}

  public isAuthenticated(): boolean {
    const token = localStorage.getItem("access_token");

    return !this.jwtHelper.isTokenExpired(token);
  }

  public login(credentials: User) {
    return this.http.post<any>(`${this.endpoint}/login`, credentials);
  }

  public loginSuccess(token: string) {
    const { sub } = this.jwtHelper.decodeToken(token);

    this.isLogged.next(true);
    this.name.next(sub);
  }

  public logoutSuccess() {
    this.isLogged.next(false);
    this.name.next(null);
  }

  public signup(credentials: User) {
    return this.http.post<User>(`${this.endpoint}/new`, credentials);
  }
}
