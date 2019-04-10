import { Component, OnInit } from "@angular/core";
import { UserService } from "@services/user/user.service";
import { NzMessageService } from "ng-zorro-antd";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"]
})
export class UserComponent implements OnInit {
  listUsers = [];
  constructor(
    private userService: UserService,
    private message: NzMessageService
  ) {}

  ngOnInit() {
    this.userService.getUsers().subscribe(data => {
      this.listUsers = [].concat(data);
    });
  }

  disableUser(id: number) {
    this.userService.disableUser(id).subscribe(() => {
      this.userService.getUsers().subscribe(data => {
        this.listUsers = [].concat(data);
      });

      this.message.create("success", "Success");
    });
  }
}
