import { Component, OnInit } from '@angular/core';
import { FileUploader, FileSelectDirective} from 'ng2-file-upload';
import {FileFunctionsService} from '../../services/file_functions/file-functions.service';
import {FileSystemDirectoryEntry, FileSystemEntry, FileSystemFileEntry, UploadEvent, UploadFile} from 'ngx-file-drop';
import {forEach} from '../../../../node_modules/@angular/router/src/utils/collection';
import {HttpClient, HttpHeaders, HttpParams} from '../../../../node_modules/@angular/common/http';

import {VersionService} from '../../services/versions/versions.service';
import { Version } from 'src/app/models/Version';
import { DeclareFunctionStmt } from '@angular/compiler';
import { CookieService } from 'angular2-cookie/services/cookies.service';



const httpOptions = {
  headers: new HttpHeaders()
};


@Component({
  selector: 'app-file-functions',
  templateUrl: './file-functions.component.html',
  styleUrls: ['./file-functions.component.css']
})
export class FileFunctionsComponent implements OnInit {
  private serverUrl = '/api/uploadFile';
  public files: UploadFile[] = [];
  public  sendFiles: Array<File> = [];
  public  pathList: Array<String> = [];
  public versionNumber = '';
  public versionSavedMsg = '';
  public versionSaved = false;
  model: any = { };



  constructor(
    private http: HttpClient,
    private versionService: VersionService,
    private cookies: CookieService
  ) { }

  ngOnInit() {
    this.refresh();
  }

  dropped(event: UploadEvent) {
    this.files = event.files;
    let num = 0;

    for (const droppedFile of event.files) {
      // if entry is a file
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
      } else {
        const fileDir = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log('hello');
        this.fileSearch(fileDir);
      }
    }
    console.log('NO');
    console.log(this.sendFiles.length);
    document.getElementById('filesDrop').style.display = 'block';
    document.getElementById('button1').removeAttribute('disabled');
    document.getElementById('button2').removeAttribute('disabled');
  }
  fileSearch(fileDir: FileSystemDirectoryEntry) {
    const directoryReader = fileDir.createReader();
    const resultFiles: UploadFile[] = [];
    directoryReader.readEntries((entries => {
      // if entry is a file
      entries.forEach((entry => {
        if (entry.isFile) {
          const fileEntry = entry as FileSystemFileEntry;
          fileEntry.file((file: File) => {
            console.log(file.name);
          });
        } else {
          // if entry is a directory
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

  createVersion() {

    const newVersion = new Version(null, this.versionNumber, null);
    const username = this.cookies.get('username');
    newVersion.username = username;

    console.log(newVersion);
    this.versionService.create(newVersion)
      .subscribe(
        (version: Version) => {
          console.log('FileFunctionsComponent.createVersion: returned', version);
          if (version && version !== null) {
            console.log('FileFunctionsComponent.createVersion: success', version);
            // add a visible message version created
            this.versionSavedMsg = 'New Version created.';
            this.versionSaved = true;
            this.refresh();
          } else {
            console.log('FileFunctionsComponent.createVersion: version not created');
            this.versionSavedMsg = 'Error creating version.';
            this.versionSaved = false;
          }
        },
        (err: any) => {
          console.log('FileFunctionsComponent.createVersion: error', err);
          this.versionSavedMsg = 'Error creating version.';
          this.versionSaved = true;
        }
      );
  }

  private refresh(): void {
    this.versionService.getAll().subscribe(
        (versions: Array<Version>) => {
          console.log('versions', versions);
            this.model.versions = versions;
        },
        (err: any) => {
            console.log('VersionComponent: error getting users', err);
        }
    );
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

    uploadData.append('verNumber', JSON.stringify(this.versionNumber));
    this.http.post(this.serverUrl, uploadData , httpOptions)
      .subscribe(
        res => {
        console.log('file-DeclareFunctionStmt.onUpload: results: ', res);
        },
        err => {
          console.log('file-DeclareFunctionStmt.onUpload: error: ', err);
        }
      );
      location.reload();
  }

  deleteVersion(event: any) {
    const verNum = event.target.dataset['vernum'];

    this.versionService.deleteByVerNum(verNum)
      .subscribe(
        res => {
          this.refresh();
          console.log('file-DeclareFunctionStmt.deleteVersion: results: ', res);
        },
        err => {
          this.refresh();
          console.log('file-DeclareFunctionStmt.deleteVersion: error: ', err);
        }
      );
  }


  clear() {
    document.getElementById('filesDrop').style.display = 'none';
    this.sendFiles.length = 0;
    this.sendFiles = [];
    this.pathList.length = 0;
    this.pathList = [];
    this.files.length = 0;
    this.files = [];

    document.getElementById('button1').setAttribute('disabled', 'true');
    document.getElementById('button2').setAttribute('disabled', 'true');
  }
  get diagnostics() {
    return 'model = ' + JSON.stringify(this.model);
  }

}

