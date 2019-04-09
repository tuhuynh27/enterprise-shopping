import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Product } from "@models/product";
import { CategoryService } from "@services/category/category.service";
import { Category } from "@models/category";

@Component({
  selector: "app-category-view",
  templateUrl: "./category-view.component.html",
  styleUrls: ["./category-view.component.scss"]
})
export class CategoryViewComponent implements OnInit {
  cateId = 0;
  cateName = "";
  listCategories: Array<Category> = [];
  listProducts: Array<Product> = [];

  loading = false;

  constructor(
    private router: ActivatedRoute,
    private redirectRouter: Router,
    private categoryService: CategoryService
  ) {}

  ngOnInit() {
    this.loading = true;

    setTimeout(() => {
      this.loading = false;
    }, 400);

    this.router.paramMap.subscribe(data => {
      this.cateId = parseInt(data.get("id"), 10) || 0;

      if (this.cateId === 0) {
        this.redirectRouter.navigate(["/"]);
      } else {
        this.loading = true;

        setTimeout(() => {
          this.loading = false;
        }, 400);

        this.categoryService.getCategories().subscribe(cates => {
          const list = [].concat(cates);

          const index = list.findIndex(e => e.id === this.cateId);
          this.cateName = list[index].name || "";

          this.getCategoryProducts();
        });
      }
    });
  }

  getCategoryProducts() {
    this.categoryService.getCategoryProducts(this.cateId).subscribe(data => {
      this.listProducts = [].concat(data);
    });
  }
}
