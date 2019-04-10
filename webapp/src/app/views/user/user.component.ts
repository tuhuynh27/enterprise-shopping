import { Component, OnInit } from "@angular/core";
import { UserService } from "@services/user/user.service";

@Component({
  selector: "app-user",
  templateUrl: "./user.component.html",
  styleUrls: ["./user.component.scss"]
})
export class UserComponent implements OnInit {
  listUsers = [];
  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getUsers().subscribe(data => {
      this.listUsers = [].concat(data);
    });
  }
}
