import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FileFunctionsService {
  private serverUrl = 'http://localhost:8080/uploadFile';


  constructor(
    private http: HttpClient
  ) { }

  sendFiles(formdata: FormData): Observable<any> {
    console.log('stuff');
      return this.http.post(this.serverUrl, formdata , httpOptions);
  }
}
