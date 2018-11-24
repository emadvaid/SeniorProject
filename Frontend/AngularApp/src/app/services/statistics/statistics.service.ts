import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { HttpClient, HttpHeaders } from '@angular/common/http';


// Add in JSON header and access token header
// const headers = new Headers({
//     'Content-Type': 'application/Json'
// });
// const options = new RequestOptions({ headers: headers });
const httpOptions = {
    headers: new HttpHeaders()
  };
@Injectable({
    providedIn: 'root'
})
export class StatisticsService {

    constructor(
        private http: HttpClient,
    ) { }

    getNewKeys(language: string, versionNumber: string) {
        const tableInfo = new FormData();
        tableInfo.append('language', language);
        tableInfo.append('versionNumber', versionNumber);

        return this.http.post('/api/statistics/new', tableInfo, httpOptions);
    }

    getApprovedKeys(language: string, versionNumber: string) {
        const tableInfo = new FormData();
        tableInfo.append('language', language);
        tableInfo.append('versionNumber', versionNumber);
        return this.http.post('/api//statistics/approved', tableInfo, httpOptions);
    }

    getTotalKeys(language: string, versionNumber: string) {
        const tableInfo = new FormData();
        tableInfo.append('language', language);
        tableInfo.append('versionNumber', versionNumber);
        return this.http.post('/api//statistics/total', tableInfo, httpOptions);
    }

    getTotalFiles(language: string, versionNumber: string) {
        const tableInfo = new FormData();
        tableInfo.append('language', language);
        tableInfo.append('versionNumber', versionNumber);
        return this.http.post('/api//statistics/files', tableInfo, httpOptions);
    }
}
