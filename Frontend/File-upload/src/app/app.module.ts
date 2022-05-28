import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { FileUploaderComponent } from './file-uploader.component';
import { FileUploaderService } from './file-uploader.service';

import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [
    BrowserModule, 
    FormsModule, 
    HttpClientModule, 
    TableModule, 
    DialogModule,
    ButtonModule],
  declarations: [AppComponent, FileUploaderComponent],
  providers: [FileUploaderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
