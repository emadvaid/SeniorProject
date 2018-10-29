import { Component, OnInit } from '@angular/core';
import { StatisticsService }  from '../../../services/statistics/statistics.service';
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  newKeys: string;
  approvedKeys: string;
  totalKeys: string;
  public versionNumber = '';
  public language = '';

  constructor(
    private statisticsService: StatisticsService,
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    this.statisticsService.getNewKeys(this.language, this.versionNumber).subscribe(
      (keys) => {
        this.newKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getApprovedKeys(this.language, this.versionNumber).subscribe(
      (keys) => {
        this.approvedKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getTotalKeys(this.language, this.versionNumber).subscribe(
      (keys) => {
        this.totalKeys = JSON.stringify(keys);
      }
    )
  }
}