import { Component, OnInit } from '@angular/core';
import {FileFunctionsService} from '../../services/file_functions/file-functions.service';
import { HttpClient, HttpHeaders, HttpClientModule } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-file-functions-redo',
  templateUrl: './file-functions-redo.component.html',
  styleUrls: ['./file-functions-redo.component.css']
})
export class FileFunctionsRedoComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/uploadFile';
  selectedFiles: Array<File> = [];
  result = '';

  constructor(
    private fileService: FileFunctionsService,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
  }

  selectFiles(event: any): void {
    this.selectedFiles = event.target.files;
    this.result = 'Number of files: ' + event.target.files.length;
    for (var i = 0; i < event.target.files.length; i++) {
      this.result += '<br>File name: ' + this.selectedFiles[i].name;
      console.log(this.selectedFiles[i].name);
    }

  }


  onUpload() {
    console.log('File');
    const uploadData = new FormData();
    for (let file of this.selectedFiles){
      console.log('file entered');
      uploadData.append('file', file, file.name);
    }
    this.http.post(this.serverUrl, uploadData , httpOptions).subscribe(res => {
      console.log(res);
    });
  }
  }
