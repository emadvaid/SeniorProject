import { Component, OnInit } from '@angular/core';
import { StatisticsService }  from '../../../services/statistics/statistics.service';
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  firstNewKeys: string;
  firstApprovedKeys: string;
  firstTotalKeys: string;
  public versionNumber = '';
  public language = '';
  secondNewKeys: string;
  secondApprovedKeys: string;
  secondTotalKeys: string;
  public versionNumber2 = '';

  constructor(
    private statisticsService: StatisticsService,
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    //Statistics for first version
    this.statisticsService.getNewKeys(this.language, this.versionNumber).subscribe(
      (keys) => {
        this.firstNewKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getApprovedKeys(this.language, this.versionNumber).subscribe(
      (keys) => {
        this.firstApprovedKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getTotalKeys(this.language, this.versionNumber2).subscribe(
      (keys) => {
        this.firstTotalKeys = JSON.stringify(keys);
      }
    )

    //Statistics for second version
    this.statisticsService.getNewKeys(this.language, this.versionNumber2).subscribe(
      (keys) => {
        this.secondNewKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getApprovedKeys(this.language, this.versionNumber2).subscribe(
      (keys) => {
        this.secondApprovedKeys = JSON.stringify(keys);
      }
    )
    this.statisticsService.getTotalKeys(this.language, this.versionNumber2).subscribe(
      (keys) => {
        this.secondTotalKeys = JSON.stringify(keys);
      }
    )
  }
}