import { Component, OnInit } from "@angular/core";
import { CategoryService } from "@services/category/category.service";
import { Category } from "@models/category";
import { AuthService } from "@services/auth/auth.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"]
})
export class AppComponent implements OnInit {
  isCollapsed = false;
  listCategories: Array<Category> = [];

  isLogged = false;

  constructor(
    private categoryService: CategoryService,
    private authService: AuthService
  ) {
    this.categoryService.listCategories.subscribe(data => {
      this.listCategories = data.filter(e => e.valid === true);
    });

    this.authService.isLogged.subscribe(status => {
      this.isLogged = status;
    });
  }

  ngOnInit() {
    this.categoryService.getCategories().subscribe(data => {
      this.categoryService.setListCategories(data);
    });
  }
}
