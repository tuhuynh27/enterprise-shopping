import { Component, OnInit } from "@angular/core";
import { FakeService } from "../fake.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements OnInit {
  photos: Array<any> = [];

  constructor(private fakeService: FakeService) {}

  ngOnInit() {
    this.fakeService.getPhotos().subscribe(data => {
      this.photos = data;
    });
  }
}
