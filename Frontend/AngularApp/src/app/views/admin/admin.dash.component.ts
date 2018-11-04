import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../../services/statistics/statistics.service';

@Component({
  selector: 'app-admin-dash',
  templateUrl: './admin.dash.component.html'
})
export class AdminDashboardComponent implements OnInit {  firstNewKeys: string;
  firstApprovedKeys: string;
  firstTotalKeys: string;
  secondNewKeys: string;
  secondApprovedKeys: string;
  secondTotalKeys: string;
  firstVersion: string;
  secondVersion: string;

  constructor(
    private statisticsService: StatisticsService,
  ) { }

  ngOnInit() {
  }

  viewStatistics(language: string, versionNumber: string) {
    this.firstVersion = versionNumber;
    //Statistics for first version
    this.statisticsService.getNewKeys(language, versionNumber).subscribe(
      (keys) => {
        this.firstNewKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getApprovedKeys(language, versionNumber).subscribe(
      (keys) => {
        this.firstApprovedKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getTotalKeys(language, versionNumber).subscribe(
      (keys) => {
        this.firstTotalKeys = JSON.stringify(keys);
      }
    )
  }

  compareVersions(language: string, versionNumber: string) {
    this.secondVersion = versionNumber;
    //Statistics for second version
    this.statisticsService.getNewKeys(language, versionNumber).subscribe(
      (keys) => {
        this.secondNewKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getApprovedKeys(language, versionNumber).subscribe(
      (keys) => {
        this.secondApprovedKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getTotalKeys(language, versionNumber).subscribe(
      (keys) => {
        this.secondTotalKeys = JSON.stringify(keys);
      }
    )
  }
}
