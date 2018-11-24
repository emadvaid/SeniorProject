import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers, RequestOptions } from '@angular/Http';

const httpOptions = {
  headers: new HttpHeaders()
};

@Injectable({
  providedIn: 'root'
})
export class LogsService {
    getLogsURL = '/api/logs';

  constructor(
      private http: HttpClient,
  ) { }

  getLogs(): Observable<any> {
    return this.http.get(this.getLogsURL, httpOptions);
  }
}
