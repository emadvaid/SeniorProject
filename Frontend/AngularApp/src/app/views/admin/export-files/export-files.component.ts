import { Component, OnInit } from '@angular/core';
import { FileFunctionsService } from '../../../services/file_functions/file-functions.service';


@Component({
  selector: 'app-export-files',
  templateUrl: './export-files.component.html',
  styleUrls: ['./export-files.component.css']
})
export class ExportFilesComponent implements OnInit {
  public versionNumber = '';
  public language = '';

  constructor(
    private fileFunctionsService: FileFunctionsService,
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    this.fileFunctionsService.exportFiles(this.language, this.versionNumber).subscribe();
  }
}
