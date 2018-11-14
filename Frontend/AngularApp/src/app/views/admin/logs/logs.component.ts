import { Component, OnInit } from '@angular/core';
import { LogsService } from '../../../services/logs/logs.service';
import { Logs } from 'selenium-webdriver';


@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.css']
})
export class LogsComponent implements OnInit {
  Logs: Logs;

  constructor(
    private logsService: LogsService,
  ) { }

  ngOnInit() {
    this.getLogs();
  }

  async getLogs() {
    //const logList = await this.logsService.getLogs().toPromise();
    //this.list = logList.;
    this.Logs = await this.logsService.getLogs().toPromise();
    console.log(this.Logs);
  }
}
