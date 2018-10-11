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

  sendFiles(selectedFiles: Array<File> = []): Observable<any> {
    const uploadData = new FormData();
    console.log('stuff');
    for (let file of selectedFiles) {
      console.log('file entered');
      uploadData.append('file', file, file.name);
    }
      return this.http.post(this.serverUrl, uploadData , httpOptions);
  }
}
