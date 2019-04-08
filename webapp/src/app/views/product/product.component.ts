import { Component, OnInit } from "@angular/core";
import { Product } from "@models/product";
import { ProductService } from "@services/product/product.service";
import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { NzMessageService } from "ng-zorro-antd";
import { Category } from "@models/category";
import { CategoryService } from "@services/category/category.service";
import { Supplier } from "@models/supplier";
import { SupplierService } from "@services/supplier/supplier.service";

@Component({
  selector: "app-product",
  templateUrl: "./product.component.html",
  styleUrls: ["./product.component.scss"]
})
export class ProductComponent implements OnInit {
  listProducts: Array<Product> = [];
  listCategories: Array<Category> = [];
  listSuppliers: Array<Supplier> = [];
  addModalVisible = false;
  addForm: FormGroup;
  updateModalVisible = false;
  updateForm: FormGroup;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private supplierService: SupplierService,
    private fb: FormBuilder,
    private message: NzMessageService
  ) {}

  ngOnInit() {
    this.addForm = this.fb.group({
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]],
      quantity: [null, [Validators.required]],
      thumbnail: [null, [Validators.required]],
      category: this.fb.group({
        id: [null, [Validators.required]]
      }),
      supplier: this.fb.group({
        id: [null, [Validators.required]]
      })
    });

    this.updateForm = this.fb.group({
      id: [null],
      name: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]],
      quantity: [null, [Validators.required]],
      thumbnail: [null, [Validators.required]],
      category: this.fb.group({
        id: [null, [Validators.required]]
      }),
      supplier: this.fb.group({
        id: [null, [Validators.required]]
      }),
      valid: [null],
      modified: [null]
    });

    this.productService.getProducts().subscribe(data => {
      this.listProducts = this.listProducts.concat(data);
    });

    this.categoryService.getCategories().subscribe(data => {
      this.listCategories = this.listCategories.concat(data);
    });

    this.supplierService.getSuppliers().subscribe(data => {
      this.listSuppliers = this.listSuppliers.concat(data);
    });
  }

  toggleAddModal() {
    this.addForm.reset();

    this.addModalVisible = !this.addModalVisible;
  }

  handleAdd() {
    console.log(this.addForm.value);

    // tslint:disable-next-line: forin
    for (const i in this.addForm.controls) {
      this.addForm.controls[i].markAsDirty();
      this.addForm.controls[i].updateValueAndValidity();
    }

    if (this.addForm.valid) {
      this.productService.createProduct(this.addForm.value).subscribe(() => {
        // Refresh
        this.productService.getProducts().subscribe(refresh => {
          this.listProducts = [].concat(refresh);
        });

        this.toggleAddModal();
      });
    }
  }

  toggleUpdateModal(id?: number) {
    if (id) {
      const updatingProduct = this.listProducts.find(e => e.id === id);

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
          this.listProducts = this.listProducts.map(e => {
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
      this.listProducts = this.listProducts.filter(e => e.id !== id);
    });
  }
}
