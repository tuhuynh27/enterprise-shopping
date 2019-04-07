import { Component, OnInit } from "@angular/core";
import { Category } from "@models/category";
import { CategoryService } from "@services/category/category.service";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-category",
  templateUrl: "./category.component.html",
  styleUrls: ["./category.component.scss"]
})
export class CategoryComponent implements OnInit {
  listCategories: Array<Category> = [];
  addModalVisible = false;
  addForm: FormGroup;
  updateModalVisible = false;
  updateForm: FormGroup;

  constructor(
    private categoryService: CategoryService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.addForm = this.fb.group({
      id: [null],
      name: ["null", [Validators.required]]
    });

    this.updateForm = this.fb.group({
      id: [null],
      name: [null, [Validators.required]],
      valid: [null, [Validators.required]],
      modified: [null]
    });

    this.categoryService.getCategories().subscribe(data => {
      this.listCategories = this.listCategories.concat(data);
    });
  }

  toggleAddModal() {
    this.addForm.reset();

    this.addModalVisible = !this.addModalVisible;
  }

  handleAdd() {
    // tslint:disable-next-line: forin
    for (const i in this.addForm.controls) {
      this.addForm.controls[i].markAsDirty();
      this.addForm.controls[i].updateValueAndValidity();
    }

    if (this.addForm.valid) {
      this.categoryService
        .createCategory(this.addForm.value)
        .subscribe(data => {
          this.listCategories = this.listCategories.concat(data);

          this.toggleAddModal();
        });
    }
  }

  toggleUpdateModal(id?: number) {
    if (id) {
      const updatingCategory = this.listCategories.find(c => c.id === id);

      this.updateForm.setValue(updatingCategory);
    } else {
      this.updateForm.reset();
    }

    this.updateModalVisible = !this.updateModalVisible;
  }

  handleUpdate() {
    // tslint:disable-next-line: forin
    for (const i in this.updateForm.controls) {
      this.updateForm.controls[i].markAsDirty();
      this.updateForm.controls[i].updateValueAndValidity();
    }

    if (this.updateForm.valid) {
      this.categoryService
        .updateCategory(this.updateForm.value)
        .subscribe(data => {
          this.listCategories = this.listCategories.map(c => {
            if (c.id === data.id) {
              return data;
            } else {
              return c;
            }
          });

          this.toggleUpdateModal();
        });
    }
  }

  handleDelete(id: number) {
    this.categoryService.deleteCategory(id).subscribe(() => {
      this.listCategories = this.listCategories.filter(c => c.id !== id);
    });
  }
}
