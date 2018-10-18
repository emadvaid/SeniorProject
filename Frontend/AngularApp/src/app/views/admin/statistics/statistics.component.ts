import { Component, OnInit } from '@angular/core';
import { StatisticsService }  from '../../../services/statistics/statistics.service';
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  newKeys: number;
  approvedKeys: number;

  constructor(
    private statisticsService: StatisticsService,
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    this.statisticsService.getNewKeys().subscribe(
      (newKeys) => {
        this.newKeys = newKeys.json();
      }
    );
    this.statisticsService.getApprovedKeys().subscribe(
      (approvedKeys) => {
        this.approvedKeys = approvedKeys.json();
      }
    );
  }
}
