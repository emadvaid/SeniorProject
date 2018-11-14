import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { LanguageKey } from '../../models/LanguageKey';

const httpOptions = {
  headers: new HttpHeaders()
};

@Injectable({
  providedIn: 'root'
})
export class KeysService {
  getAllKeysURL = 'http://localhost:8080/getAllKeys';
  updateKeyURL = 'http://localhost:8080/updateKeyValues';

  constructor(
    private http: HttpClient,
  ) { }

  getNewKeys(language: string, versionNumber: string): Observable<any> {
    const tableInfo = new FormData();
    tableInfo.append('language', language);
    tableInfo.append('versionNumber', versionNumber);
    return this.http.post(this.getAllKeysURL, tableInfo, httpOptions);
  }

  updateKey(key: LanguageKey) {
    return this.http.put(this.updateKeyURL, key, httpOptions);
  }
}
