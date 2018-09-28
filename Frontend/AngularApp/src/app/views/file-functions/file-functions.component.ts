import { Component, OnInit } from '@angular/core';
import { FileUploader, FileSelectDirective} from 'ng2-file-upload';

const URL = '';

@Component({
  selector: 'app-file-functions',
  templateUrl: './file-functions.component.html',
  styleUrls: ['./file-functions.component.css']
})
export class FileFunctionsComponent implements OnInit {

  public uploader: FileUploader = new FileUploader({ url: URL, itemAlias: 'XML'});

  constructor() { }
  indicator = '';
  trueFalse = false;

  ngOnInit() {
    this.uploader.onAfterAddingFile = (file) => {file.withCredentials = false;
    if(file.file.name.substring(file.file.name.length - 3) !== 'xml'){
      this.trueFalse = false;
      this.indicator = 'File must be in XML format';
      this.removeFromQueue('xml');
    }
    else {
      this.indicator = '';
      this.trueFalse = true;
    }
    };
    this.uploader.onWhenAddingFileFailed = (items) => {
      console.log(items);





    }
    this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
      console.log('XMLUploaded', item, status, response);
      alert('File uploaded succesfully');
    };
  }

  removeFromQueue(label: string){
    for(var i = 0; i < this.uploader.queue.length; i++) {
      let temp = '';
       temp = this.uploader.queue[i].file.name;

      if (temp.substring(temp.length - 3) !== label) {
        this.uploader.queue[i].remove();
      }
    }
  }


}
