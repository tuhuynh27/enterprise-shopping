import { Component, OnInit } from "@angular/core";
import { unmarshal, addToCart, emptyCart } from "@utils/cart.utils";
import { ProductService } from "@services/product/product.service";
import { NzMessageService } from "ng-zorro-antd";
import { Router } from "@angular/router";
import { OrderService } from "@services/order/order.service";
import { OrderDetailService } from "@services/orderDetail/order-detail.service";
import { AuthService } from "@services/auth/auth.service";

@Component({
  selector: "app-cart",
  templateUrl: "./cart.component.html",
  styleUrls: ["./cart.component.scss"]
})
export class CartComponent implements OnInit {
  listCart = [];
  loading = false;
  name = "";
  totalFee = 0;
  constructor(
    private productService: ProductService,
    private orderService: OrderService,
    private orderDetailService: OrderDetailService,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router
  ) {
    this.authService.name.subscribe(data => {
      this.name = data;
    });
  }

  ngOnInit() {
    this.loadCart();
  }

  loadCart() {
    const cart = unmarshal();
    let list = [];

    if (!cart || !cart.length) {
      this.listCart = [];
      return;
    }

    cart.map(e => {
      this.productService.getProduct(e.id).subscribe(data => {
        list = list.concat({
          id: e.id,
          name: data.name,
          thumbnail: data.thumbnail,
          price: data.price,
          quantity: e.quantity
        });

        list.sort((a, b) => a.id - b.id);
        this.listCart = [].concat(list);
        this.totalFee = this.listCart.reduce((total, e) => {
          return total + e.price * e.quantity;
        }, 0);
      });
    });
  }

  modifyCart(id: number, quantity: number) {
    this.loading = true;

    setTimeout(() => {
      this.loading = false;
    }, 400);

    addToCart(id, quantity);

    this.loadCart();
  }

  empty() {
    this.loading = true;

    setTimeout(() => {
      this.loading = false;
    }, 400);

    emptyCart();

    this.loadCart();
  }

  checkout() {
    if (!localStorage.getItem("access_token")) {
      this.message.create("error", "You must be logged in before checkout");
      this.router.navigate(["/login"]);
    }

    this.orderService.createOrder(1, this.name).subscribe(data => {
      const cart = unmarshal();

      cart.map(e => {
        this.orderDetailService
          .createOrderDetail(data.id, e.id, e.quantity)
          .subscribe(() => {});
      });
    });

    this.loading = true;

    setTimeout(() => {
      this.loading = false;
      this.message.create(
        "success",
        "Thank you for you order! You can view your orders at 'My order' menu."
      );
      this.empty();
      this.router.navigate(["/"]);
    }, 2000);
  }
}
