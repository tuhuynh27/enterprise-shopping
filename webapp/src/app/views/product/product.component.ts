import { Component, OnInit } from "@angular/core";
import { Product } from "@models/product";
import { ProductService } from "@services/product/product.service";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-product",
  templateUrl: "./product.component.html",
  styleUrls: ["./product.component.scss"]
})
export class ProductComponent implements OnInit {
  listCategories: Array<Product> = [];
  addModalVisible = false;
  addForm: FormGroup;
  updateModalVisible = false;
  updateForm: FormGroup;

  constructor(
    private productService: ProductService,
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

    this.productService.getProducts().subscribe(data => {
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
      this.productService.createProduct(this.addForm.value).subscribe(data => {
        this.listCategories = this.listCategories.concat(data);

        this.toggleAddModal();
      });
    }
  }

  toggleUpdateModal(id?: number) {
    if (id) {
      const updatingProduct = this.listCategories.find(e => e.id === id);

      this.updateForm.setValue(updatingProduct);
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
      this.productService
        .updateProduct(this.updateForm.value)
        .subscribe(data => {
          this.listCategories = this.listCategories.map(e => {
            if (e.id === data.id) {
              return data;
            } else {
              return e;
            }
          });

          this.toggleUpdateModal();
        });
    }
  }

  handleDelete(id: number) {
    this.productService.deleteProduct(id).subscribe(() => {
      this.listCategories = this.listCategories.filter(e => e.id !== id);
    });
  }
}
