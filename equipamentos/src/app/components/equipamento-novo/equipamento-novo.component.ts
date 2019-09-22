import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Equipamento } from 'src/app/model/equipamento.model';
import { EquipamentoService } from 'src/app/services/equipamento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/model/responseApi.model';
import { TipoEquipamento } from 'src/app/model/enums/TipoEquipamento';
//import {  FileUploader, FileSelectDirective, FileItem } from 'ng2-file-upload/ng2-file-upload';

@Component({
  selector: 'app-equipamento-novo',
  templateUrl: './equipamento-novo.component.html'
})
export class EquipamentoNovoComponent implements OnInit {

  @ViewChild("form", {static: true}) form: NgForm;

  startDate = new Date(1990, 0, 1);
  equipamento = new Equipamento(0, null, '', null, 0, null, null);
  menssage: {type: string, text: string};
  classCss: {}
  tipos = new Array<string>();

  ngAfterViewInit() {
  }
  
  //uploader: FileUploader = new FileUploader({ url: "equipamento.foto", removeAfterUpload: false, autoUpload: true });

  constructor(private equipamentoService: EquipamentoService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { 
      //this.tipos =  Object.keys(TipoEquipamento);
      
    }

  ngOnInit() {
    let id: number = this.activatedRoute.snapshot.params['id'];
    if(id != undefined){
      this.findById(id);
    }
    this.tipos = Object.keys(TipoEquipamento);
  }

  save(){
    this.equipamentoService.createOrUpdate(this.equipamento).subscribe((responseApi: ResponseApi) => {
      this.equipamento = responseApi.data;
      this.showMessage({
        type: 'success',
        text: `Equipamento registrado ${this.equipamento.modelo} com sucesso`
      });
    }, err =>{
      this.showMessage({
        type: 'error',
        text: err["error"]
      });
    });    
  }
//["erros"][0]
  findById(id: number) {
    this.equipamentoService.findById(id).subscribe((responseApi: ResponseApi) =>{
      this.equipamento = responseApi.data;
      //this.uploader.uploadItem(new FileItem(this.uploader, this.equipamento.foto, this.uploader.autoUpload));
      //this.uploader.setOptions(this.equipamento.foto);
      //this.form.controls.foto.setValue(this.equipamento.foto);
    }, err =>{
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  private showMessage(message: {type: string, text: string}) : void {
    this.menssage = message;
    this.buildClasses(message.type);
    setTimeout(()=>{
      this.menssage = undefined;
    }, 8000);
  }

  private buildClasses(type: string) : void {
    this.classCss = {
      'alert' : true
    }
    this.classCss['alert-'+type] = true;
  }

  getFormGroupClass(isInvalid: boolean, isDirty: boolean): {} {
    return {
      'form-group': true,
      'has-error': isInvalid && isDirty,
      'has-success': !isInvalid && isDirty
    };
  }

  onFileChange($event): void {

    if($event && $event.target.files[0].size > 2000000){
      this.showMessage({
        type: 'error',
        text: 'Tamanho Maximo da imagem 2MB'
      })
    } else {
      var reader = new FileReader();
      reader.onloadend = ($event) =>{
        this.equipamento.foto = reader.result;
      }
      reader.readAsDataURL($event.target.files[0]);
    }

    //if($event == undefined || $event == null && equipamento != null){
      
      //document.getElementsByName("inputFoto").value = equipamento.foto
      //var img = new Image;
      //img.src = e.target.result;
      //img.onload = function () {}
      //var reader = new FileReader();
      //reader.onloadend = (e: Event) =>{
        //reader.readAsDataURL = this.equipamento.foto;
      //}
      //reader.readAsDataURL(equipamento.foto);
    } 
  


  fileChanged(e) {
    const reader = new FileReader();
    reader.onload = () => {
      this.equipamento.foto = reader.result;
    };
    reader.readAsText(e.target.files[0]);
    console.log(e.target.files[0]);
  }
}
