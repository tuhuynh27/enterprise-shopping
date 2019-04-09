import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { AuthService } from "@services/auth/auth.service";

import { NzMessageService } from "ng-zorro-antd";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
  validateForm: FormGroup;

  submitForm(): void {
    // tslint:disable-next-line: forin
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
  }

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userName: [null, [Validators.required, Validators.minLength(1)]],
      password: [null, [Validators.required, Validators.minLength(1)]]
    });

    const token = localStorage.getItem("access_token");
    if (!this.authService.jwtHelper.isTokenExpired(token)) {
      this.router.navigate(["/"]);
    }
  }

  submitLogin() {
    if (this.validateForm.valid) {
      this.authService
        .login({
          name: this.validateForm.value.userName,
          password: this.validateForm.value.password,
          email: null
        })
        .subscribe(
          data => {
            this.authService.loginSuccess(data.token);

            // Save
            localStorage.setItem("access_token", data.token);

            this.message.create(
              "success",
              `Welcome <strong>${data.name}</strong> back, have a nice day!`
            );

            this.router.navigate(["/"]);
          },
          () => {
            this.message.create("error", "Username or password is wrong!");
          }
        );
    } else {
      this.validateForm.markAsDirty();
    }
  }
}
