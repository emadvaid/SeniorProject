import { Component, OnInit } from '@angular/core';
import { FileUploader, FileSelectDirective} from 'ng2-file-upload';
import {FileFunctionsService} from '../../services/file_functions/file-functions.service';
import {FileSystemDirectoryEntry, FileSystemEntry, FileSystemFileEntry, UploadEvent, UploadFile} from 'ngx-file-drop';
import {forEach} from '../../../../node_modules/@angular/router/src/utils/collection';
import {HttpClient, HttpHeaders, HttpParams} from '../../../../node_modules/@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders()
};


@Component({
  selector: 'app-file-functions',
  templateUrl: './file-functions.component.html',
  styleUrls: ['./file-functions.component.css']
})
export class FileFunctionsComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/uploadFile';
  public files: UploadFile[] = [];
  public  sendFiles: Array<File> = [];
  public  pathList: Array<String> = [];


  constructor(
    private http: HttpClient
  ) { }

  ngOnInit() {
  }

  dropped(event: UploadEvent) {
    this.files = event.files;
    let num = 0;

    for (const droppedFile of event.files) {
      //if entry is a file
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          this.sendFiles[num] = file;
          this.pathList[num] = droppedFile.relativePath;
          num++;
          console.log(file.name);
          console.log(droppedFile.relativePath);
          console.log(this.sendFiles.length);
        });
      }
      //entry is a directory
      else {
        const fileDir = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log('hello');
        this.fileSearch(fileDir);
      }
    }
    console.log('NO');
    console.log(this.sendFiles.length);
    document.getElementById("filesDrop").style.display = "block";
    document.getElementById("button1").removeAttribute("disabled");
    document.getElementById("button2").removeAttribute("disabled");
  }
  fileSearch(fileDir: FileSystemDirectoryEntry) {
    const directoryReader = fileDir.createReader();
    let resultFiles: UploadFile[] = [];
    directoryReader.readEntries((entries => {
      //if entry is a file
      entries.forEach((entry => {
        if (entry.isFile) {
          const fileEntry = entry as FileSystemFileEntry;
          fileEntry.file((file: File) => {
            console.log(file.name);
          });
        }
        //if entry is a directory
        else {
          const entry2 = entry as FileSystemDirectoryEntry;
          this.fileSearch(entry2);
        }
      }));
    }));
    }

   fileOver(event) {
    console.log(event);
  }

   fileLeave(event) {
    console.log(event);
  }


  onUpload() {
    console.log('File');
    const uploadData = new FormData();
    let i = 0;
    for (const file of this.sendFiles) {
      console.log('file entered');
      uploadData.append('file', file);
      uploadData.append('path', JSON.stringify(this.pathList[i]));
      i++;
    }
    this.http.post(this.serverUrl, uploadData , httpOptions).subscribe(res => {
      console.log(res);
    });
  }

  clear() {
    document.getElementById('filesDrop').style.display = 'none';
    this.sendFiles.length = 0;
    this.sendFiles = [];
    this.pathList.length = 0;
    this.pathList = [];
    this.files.length = 0;
    this.files = [];

    document.getElementById("button1").setAttribute("disabled", "true");
    document.getElementById("button2").setAttribute("disabled", "true");
  }
}

