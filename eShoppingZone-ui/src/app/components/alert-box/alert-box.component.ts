import { Component, Input, OnInit } from '@angular/core';
import { Alert } from 'src/app/models/Alert.model';

@Component({
  selector: 'app-alert-box',
  templateUrl: './alert-box.component.html',
  styleUrls: ['./alert-box.component.css'],
})
export class AlertBoxComponent implements OnInit {
  @Input() public alert = new Alert('', '');

  ngOnInit() {
    setInterval(() => {
      this.alert = new Alert('', '');
    }, 6000);
  }
}
