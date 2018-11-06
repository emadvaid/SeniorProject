import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Headers, RequestOptions } from '@angular/Http';
import { LanguageKey} from '../../models/LanguageKey';

const httpOptions = {
  headers: new HttpHeaders()
};

@Injectable({
  providedIn: 'root'
})
export class KeysService {
  getAllKeysURL = 'http://localhost:8080/getAllKeys';
  updateKeyURL = 'http://localhost:8080/updateKeys'

  constructor(
    private http: HttpClient,
  ) { }

  getNewKeys(language: string, versionNumber: string): Observable<any> {
    const tableInfo = new FormData();
    tableInfo.append('language', language);
    tableInfo.append('versionNumber', versionNumber);

    return this.http.post(this.getAllKeysURL, tableInfo, httpOptions);
  }

  updateKey(tableName: string, key: LanguageKey){
    const keyBody = JSON.stringify(key);
    return this.http.put(this.getAllKeysURL, {tablename: tableName, keyBody: keyBody}, httpOptions );
  }
}
