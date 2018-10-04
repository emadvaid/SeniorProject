import { Component, OnInit } from '@angular/core';
import {FileFunctionsService} from '../../services/file_functions/file-functions.service';

@Component({
  selector: 'app-file-functions-redo',
  templateUrl: './file-functions-redo.component.html',
  styleUrls: ['./file-functions-redo.component.css']
})
export class FileFunctionsRedoComponent implements OnInit {
  selectedFiles: File[];
  result = '';

  constructor(
    private fileService: FileFunctionsService
  ) { }

  ngOnInit() {
  }

  selectFiles(event: any): void {
    this.selectedFiles = event.target.files;
    this.result = 'Number of files: ' + event.target.files.length;
    for(var i = 0; i < event.target.files.length; i++){
      this.result += '<br>File name: ' + this.selectedFiles[i].name;
      console.log(this.selectedFiles[i].name);
    }

  }


  onUpload() {
    const uploadData = new FormData();
    for (let file of this.selectedFiles){
      console.log('file entered');
      uploadData.append('file', file, file.name);
    }
    console.log('File', uploadData);
    //this.fileService.sendFiles(uploadData).subscribe(event => {
        //console.log(event);
      //}
    //);
  }

}
