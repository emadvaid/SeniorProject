import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../../../services/statistics/statistics.service';
import { VersionService } from '../../../services/versions/versions.service';
import { LanguagesService } from '../../../services/languages/languages.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  firstNewKeys: string;
  firstApprovedKeys: string;
  firstTotalKeys: string;
  firstTotalFiles: string;
  secondNewKeys: string;
  secondApprovedKeys: string;
  secondTotalKeys: string;
  secondTotalFiles: string;

  firstVersion: string;
  secondVersion: string;

  compare: Boolean = true;

  currVersion = '';
  currLanguage = '';
  public versionNumber = '';
  public language = '';
  model: any = {};
  languages: any = {};
  unapprovedkeys1 = 0;
  unapprovedkeys2 = 0;



  public barChartData: number[] = [0, 0, 0];
  public barChartData2: number[] = [0, 0, 0];
  public barChartLabels: Array<any> = [['Approved', 'Keys'], ['Unapproved', 'Keys'], ['New', 'Keys']];
  public barChartType:string = 'bar';

  public colors:Array<any> = [{
    backgroundColor: ['rgba(0,128,0,0.7)', 'rgba(255,0,0,0.7)', 'rgba(255,0,0,0.7)']}
  ];

  public colors2:Array<any> = [{
    backgroundColor: ['rgba(255,0,255,0.7)', 'rgba(0,255,255,0.7)', 'rgba(0,255,255,0.7)']}
  ];

  public barChartOptions: any = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      display: false
    },
    tooltips: {
      callbacks: {
        label: function(tooltipItem) {
          return tooltipItem.yLabel;
        }
      }
    },
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true
        }
      }]
    }
  };

  constructor(
    private statisticsService: StatisticsService,
    private versionService: VersionService,
    private languageService: LanguagesService,
  ) { }

  async ngOnInit() {
    await this.getVersions();
    await this.getLanguages();
    this.language = this.currLanguage;
    this.versionNumber = this.currVersion;
  }

  async getVersions() {
    const versions = await this.versionService.getAll().toPromise();
    this.model.versions = versions;
    this.currVersion = this.model.versions[0].verNum;
    console.log(this.currVersion);
  }

  async getLanguages() {
    const languages = await this.languageService.getAll().toPromise();
    this.languages.lang = languages;
    console.log(languages);
    this.currLanguage = this.languages.lang[0].langCode;
    console.log(this.currLanguage);
  }

  changeLanguage() {
    this.language = this.currLanguage;
    console.log(this.language);
  }

  async viewStatistics(language: string, versionNumber: string) {
    this.firstVersion = versionNumber;
    // Statistics for first version
   let keys1 = await this.statisticsService.getNewKeys(language, versionNumber).toPromise();
   this.firstNewKeys = JSON.stringify(keys1);
    let keys2 = await this.statisticsService.getApprovedKeys(language, versionNumber).toPromise();
        this.firstApprovedKeys = JSON.stringify(keys2);
    let keys3 = await this.statisticsService.getTotalKeys(language, versionNumber).toPromise();
    this.firstTotalKeys = JSON.stringify(keys3);
    let keys4 = await this.statisticsService.getTotalFiles(language, versionNumber).toPromise();
        this.firstTotalFiles = JSON.stringify(keys4);
    this.unapprovedkeys1 = +this.firstTotalKeys - +this.firstApprovedKeys;
    this.barChartData = [+this.firstApprovedKeys, this.unapprovedkeys1, +this.firstNewKeys];
  }

  async compareVersions(language: string, versionNumber: string) {
    this.viewStatistics(this.language, this.currVersion);
    this.secondVersion = versionNumber;
    // Statistics for second version
    let keys1 = await this.statisticsService.getNewKeys(language, versionNumber).toPromise();
        this.secondNewKeys = JSON.stringify(keys1);

    let keys2 = await this.statisticsService.getApprovedKeys(language, versionNumber).toPromise();
        this.secondApprovedKeys = JSON.stringify(keys2);
    let keys3 = await this.statisticsService.getTotalKeys(language, versionNumber).toPromise();
        this.secondTotalKeys = JSON.stringify(keys3);
    let keys4 = await this.statisticsService.getTotalFiles(language, versionNumber).toPromise();
        this.secondTotalFiles = JSON.stringify(keys4);
    this.unapprovedkeys2 = +this.secondTotalKeys - +this.secondApprovedKeys;
    this.barChartData2 = [+this.secondApprovedKeys, this.unapprovedkeys2, +this.secondNewKeys];
  }

  setCompare(){
    this.compare = false;
  }
}