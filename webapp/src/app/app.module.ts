import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { NgZorroAntdModule, NZ_I18N, en_US } from "ng-zorro-antd";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { registerLocaleData } from "@angular/common";
import en from "@angular/common/locales/en";
import { LoginComponent } from "@views/login/login.component";
import { HomeComponent } from "@views/home/home.component";
import { CategoryComponent } from "@views/category/category.component";
import { ProductComponent } from "./views/product/product.component";
import { SupplierComponent } from "./views/supplier/supplier.component";

import { JwtModule } from "@auth0/angular-jwt";
import { SignupComponent } from "./views/signup/signup.component";
import { CategoryViewComponent } from "./views/category-view/category-view.component";
import { OrderComponent } from "./views/order/order.component";

import { TimeAgoPipe } from "time-ago-pipe";
import { CartComponent } from './views/cart/cart.component';

export function tokenGetter() {
  return localStorage.getItem("access_token");
}

registerLocaleData(en);

@NgModule({
  declarations: [
    TimeAgoPipe,
    AppComponent,
    LoginComponent,
    HomeComponent,
    CategoryComponent,
    ProductComponent,
    SupplierComponent,
    SignupComponent,
    CategoryViewComponent,
    OrderComponent,
    CartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgZorroAntdModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter,
        whitelistedDomains: ["localhost:3000"]
      }
    }),
    BrowserAnimationsModule
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})
export class AppModule {}
