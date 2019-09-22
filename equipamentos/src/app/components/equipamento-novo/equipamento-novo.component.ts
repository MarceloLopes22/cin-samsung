import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Equipamento } from 'src/app/model/equipamento.model';
import { EquipamentoService } from 'src/app/services/equipamento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/model/responseApi.model';
import { TipoEquipamentoEnum } from 'src/app/model/enums/TipoEquipamentoEnum';

@Component({
  selector: 'app-equipamento-novo',
  templateUrl: './equipamento-novo.component.html'
})
export class EquipamentoNovoComponent implements OnInit {

  @ViewChild("form", {static: true}) form: NgForm;

  equipamento = new Equipamento(null, null, '', '', null, null, null);
  menssage: {}
  classCss: {}
  tipos = new Array<string>();
  
  constructor(private equipamentoService: EquipamentoService,
    private activatedRoute: ActivatedRoute,
    private router: Router) { }

  ngOnInit() {
    let id: number = this.activatedRoute.snapshot.params['id'];
    if(id != undefined){
      this.findById(id);
    }
    this.tipos =  Object.keys(TipoEquipamentoEnum);
  }

  save(){
    this.menssage = {};
    this.equipamentoService.createOrUpdate(this.equipamento).subscribe((responseApi: ResponseApi) => {
      //this.equipamento = new Equipamento(null, null, '', '', null, null, null);
      //let notificationRet: Equipamento = responseApi.data;
      this.equipamento = responseApi.data;
      this.form.resetForm();
      this.showMessage({
        type: 'success',
        text: `Equipamento registrado ${this.equipamento.modelo} com sucesso`
      });
      this.router.navigate(['/list-notification']);
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

  getFormGroupClass(isInvalid: boolean, isDirty): {} {
    return {
      'form-group': true,
      'has-error': isInvalid && isDirty,
      'has-success': !isInvalid && isDirty
    };
  }

}
