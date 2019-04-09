import { Component, OnInit } from "@angular/core";

import { Product } from "@models/product";
import { ProductService } from "@services/product/product.service";

import { NzMessageService } from "ng-zorro-antd";

import { addToCart } from "@utils/cart.utils";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements OnInit {
  listProducts: Array<Product> = [];
  listProductsOrigin: Array<Product> = [];

  searchValue = "";
  loading = false;

  constructor(
    private productService: ProductService,
    private message: NzMessageService
  ) {}

  ngOnInit() {
    this.loading = true;

    setTimeout(() => {
      this.loading = false;
    }, 400);

    this.productService.getProducts().subscribe(data => {
      this.listProducts = this.listProducts.concat(data);
      this.listProductsOrigin = this.listProductsOrigin.concat(data);
    });
  }

  addCart(product: Product) {
    this.message.create(
      "success",
      `Added <strong>${product.name}</strong> to your cart`
    );

    addToCart(product.id, 1);
  }

  reset(): void {
    this.searchValue = "";
    this.search();
  }

  search(): void {
    if (!this.searchValue.trim()) {
      this.searchFilter();
    }

    this.loading = true;

    setTimeout(this.searchFilter, 400);
  }

  searchFilter = () => {
    this.listProducts = this.listProductsOrigin.filter(e =>
      e.name.toLocaleLowerCase().includes(this.searchValue.toLocaleLowerCase())
    );

    this.loading = false;
    // tslint:disable-next-line: semicolon
  };
}
