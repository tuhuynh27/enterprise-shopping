import { Component, OnInit } from "@angular/core";
import { OrderService } from "@services/order/order.service";
import { NzMessageService } from "ng-zorro-antd";

@Component({
  selector: "app-order",
  templateUrl: "./order.component.html",
  styleUrls: ["./order.component.scss"]
})
export class OrderComponent implements OnInit {
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
    this.orderService.getOrders().subscribe(data => {
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

  markShipped(id: any) {
    this.orderService.markShipped(id).subscribe(() => {
      this.orderService.getOrders().subscribe(data => {
        this.listOrders = [].concat(data);
      });

      this.message.create("success", "Marked as shipped!");
      this.closeModal();
    });
  }

  closeModal() {
    this.modalVisible = false;
    this.totalFee = 0;
    this.orderShipped = true;
  }
}
