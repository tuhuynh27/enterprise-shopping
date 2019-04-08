import { Component, OnInit } from "@angular/core";
import { CategoryService } from "@services/category/category.service";
import { Category } from "@models/category";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent implements OnInit {
  isCollapsed = false;
  listCategories: Array<Category> = [];

  constructor(private categoryService: CategoryService) {}

  ngOnInit() {
    this.categoryService.getCategories().subscribe(data => {
      this.listCategories = this.listCategories.concat(data);

      this.listCategories = this.listCategories.filter(e => e.valid === true);
    });
  }
}
