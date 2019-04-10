import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "@views/home/home.component";
import { LoginComponent } from "@views/login/login.component";
import { SignupComponent } from "@views/signup/signup.component";
import { CategoryComponent } from "@views/category/category.component";
import { ProductComponent } from "@views/product/product.component";
import { SupplierComponent } from "@views/supplier/supplier.component";

import { AuthGuardService as AuthGuard } from "@services/auth/auth-guard.service";
import { CategoryViewComponent } from "@views/category-view/category-view.component";
import { OrderComponent } from "@views/order/order.component";
import { CartComponent } from "@views/cart/cart.component";
import { MyorderComponent } from "@views/myorder/myorder.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "signup",
    component: SignupComponent
  },
  {
    path: "category/:id",
    component: CategoryViewComponent
  },
  {
    path: "cart",
    component: CartComponent
  },
  {
    path: "myorder",
    component: MyorderComponent
  },
  {
    path: "admin",
    canActivate: [AuthGuard],
    children: [
      {
        path: "category",
        component: CategoryComponent
      },
      {
        path: "supplier",
        component: SupplierComponent
      },
      {
        path: "product",
        component: ProductComponent
      },
      {
        path: "order",
        component: OrderComponent
      }
    ]
  },
  {
    path: "**",
    redirectTo: "/"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
