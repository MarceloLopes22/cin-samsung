import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Equipamento } from 'src/app/model/equipamento.model';
import { EquipamentoService } from 'src/app/services/equipamento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/model/responseApi.model';
import { TipoEquipamento } from 'src/app/model/enums/TipoEquipamento';

@Component({
  selector: 'app-equipamento-novo',
  templateUrl: './equipamento-novo.component.html'
})
export class EquipamentoNovoComponent implements OnInit {

  @ViewChild("form", {static: true}) form: NgForm;

  equipamento = new Equipamento(0, null, '', null, 0, null, null, '', '');
  menssage: {type: string, text: string};
  classCss: {}
  tipos = new Array<string>();
  isEdicao:Boolean = false;

  ngAfterViewInit() {
  }
  

  constructor(private equipamentoService: EquipamentoService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { 
    }

  ngOnInit() {
    let id: number = this.activatedRoute.snapshot.params['id'];
    if(id != undefined){
      this.findById(id);
      this.isEdicao = true;
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

  findById(id: number) {
    this.equipamentoService.findById(id).subscribe((responseApi: ResponseApi) =>{
      this.equipamento = responseApi.data;
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
  } 
}
