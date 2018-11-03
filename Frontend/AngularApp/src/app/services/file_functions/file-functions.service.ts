import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Version } from 'src/app/models/Version';

const httpOptions = {
  headers: new HttpHeaders()
};

@Injectable({
  providedIn: 'root'
})
export class FileFunctionsService {
  private serverUrl = 'http://localhost:8080/uploadFile';


  constructor(
    private http: HttpClient
  ) { }

  sendFiles(versionNumber: string, selectedFiles: Array<File> = []): Observable<any> {

    const uploadData = new FormData();

    console.log('stuff');
    uploadData.append('versionNumber', versionNumber);
    for (const file of selectedFiles) {
      console.log('file entered');
      uploadData.append('file', file, file.name);
    }

      return this.http.post(this.serverUrl, uploadData , httpOptions);
  }

  exportFiles(language: string, versionNumber: string) {
    const tableInfo = new FormData();
    tableInfo.append('language', language);
    tableInfo.append('versionNumber', versionNumber);
    return this.http.post('http://localhost:8080/exportFile', tableInfo, httpOptions);
  }
}
