import { Component, OnInit } from '@angular/core';
import { FileUploader, FileSelectDirective} from 'ng2-file-upload';
import {FileFunctionsService} from '../../services/file_functions/file-functions.service';
import {FileSystemDirectoryEntry, FileSystemEntry, FileSystemFileEntry, UploadEvent, UploadFile} from 'ngx-file-drop';
import {forEach} from '../../../../node_modules/@angular/router/src/utils/collection';



@Component({
  selector: 'app-file-functions',
  templateUrl: './file-functions.component.html',
  styleUrls: ['./file-functions.component.css']
})
export class FileFunctionsComponent implements OnInit {
  private serverUrl = 'http://localhost:8080/uploadFile';
  public files: UploadFile[] = [];


  constructor(

  ) { }

  ngOnInit() {
  }

  dropped(event: UploadEvent) {
    this.files = event.files;

    for (const droppedFile of event.files) {
      //if entry is a file
      if (droppedFile.fileEntry.isFile) {
        const fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        fileEntry.file((file: File) => {
          console.log(file.name);
        });
      }
      //entry is a directory
      else {
        const fileDir = droppedFile.fileEntry as FileSystemDirectoryEntry;
        this.fileSearch(fileDir);
      }
    }
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
}
