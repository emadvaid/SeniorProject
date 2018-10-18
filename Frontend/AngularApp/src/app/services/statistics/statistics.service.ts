
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { HttpClient, HttpHeaders } from '@angular/common/http';


// Add in JSON header and access token header
const headers = new Headers({
    'Content-Type': 'application/Json'
});
const options = new RequestOptions({ headers: headers });

@Injectable({
    providedIn: 'root'
})
export class StatisticsService {

    constructor(
        private http: Http,
    ) { }

    getNewKeys() {
        console.log('http://localhost:8080/statistics/new')
        return this.http.get('http://localhost:8080/statistics/new')
    }

    getApprovedKeys() {
        console.log('http://localhost:8080/statistics/approved')
        return this.http.get('http://localhost:8080/statistics/approved')
    }

    getTotalKeys() {
        return this.http.get('http://localhost:8080/statistics/total')
    }
}
