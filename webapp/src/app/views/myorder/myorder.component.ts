import { Component, OnInit } from "@angular/core";
import { OrderService } from "@services/order/order.service";
import { NzMessageService } from "ng-zorro-antd";

@Component({
  selector: "app-myorder",
  templateUrl: "./myorder.component.html",
  styleUrls: ["./myorder.component.scss"]
})
export class MyorderComponent implements OnInit {
  listOrders = [];
  orderDetailData = [];
  orderDetailId = "";
  orderShipped = true;
  totalFee = 0;
  modalVisible = false;
  loading = false;
  constructor(
    private orderService: OrderService,
    private message: NzMessageService
  ) {}

  ngOnInit() {
    this.orderService.getMyOrders().subscribe(data => {
      this.listOrders = [].concat(data);
    });
  }

  toggleView(id: any) {
    this.loading = true;

    this.orderService.getOrderDetails(id).subscribe(data => {
      this.orderDetailData = [].concat(data);
      this.orderDetailId = id;
      const index = this.listOrders.findIndex(e => e.id === id);
      this.orderShipped = this.listOrders[index].shipped;
      this.modalVisible = true;

      this.totalFee = this.orderDetailData.reduce((total, e) => {
        const price = e.orderDetailIdentify.product.price;
        const singleItemPrice =
          price * e.quantity - (price * e.quantity * e.discount) / 100;

        return total + singleItemPrice;
      }, 0);

      setTimeout(() => {
        this.loading = false;
      }, 400);
    });
  }

  closeModal() {
    this.modalVisible = false;
    this.totalFee = 0;
    this.orderShipped = true;
  }
}
