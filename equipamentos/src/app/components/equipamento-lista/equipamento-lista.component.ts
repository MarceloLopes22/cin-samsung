import { Component, OnInit } from '@angular/core';
import { DialogService } from 'src/app/services/dialog.service';
import { EquipamentoService } from 'src/app/services/equipamento.service';
import { Router } from '@angular/router';
import { ResponseApi } from 'src/app/model/responseApi.model';

@Component({
  selector: 'app-equipamento-lista',
  templateUrl: './equipamento-lista.component.html'
})
export class EquipamentoListaComponent implements OnInit {

  page:number = 0;
  count:number = 5;
  pages:Array<number>;
  menssage: {type: string, text: string};
  classCss: {};
  listaEquipamentos = [];

  constructor(
    private dialogService: DialogService,
    private equipamentoService: EquipamentoService,
    private router: Router
  ) {
   }

  ngOnInit() {
    this.findAll(this.page, this.count);
  }

  findAll(page: number, count:number) {
    this.equipamentoService.findAll(page, count).subscribe((responseApi: ResponseApi) => {
      this.listaEquipamentos = responseApi['data']['content'];
      this.pages = new Array(responseApi['data']['totalPages']);
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['erros'][0]
      });
    });
  }

  edit(id:number) {
    this.router.navigate(['/equipamento-novo', id]);
  }

  delete(id: number) {
    this.dialogService.confirm("Você quer deletar esse equipamento?")
    .then((canDelete:boolean) =>{
      if(canDelete) {
        this.equipamentoService.delete(id).subscribe((responseApi: ResponseApi) =>{
          this.showMessage({
            type: 'success',
            text: 'Registro deletado.'
          });
          this.findAll(this.page, this.count);
        }, err => {
          this.showMessage({
            type: 'error',
            text: err['error']['erros'][0]
          });
        });
      }
    });
  }

  detalhar(id:string) {
    this.router.navigate(['/detalhar-equipamento',id]);
  }

  setNextPagina(event:any) {
    event.preventDefault(); // evitar reload na tela
    if(this.page+1 < this.pages.length) {
      this.page = this.page + 1;
      this.findAll(this.page, this.count);
    }
  }

  setPreviousPagina(event:any) {
    event.preventDefault(); // evitar reload na tela
    if(this.page+1 < this.pages.length) {
      this.page = this.page - 1;
      this.findAll(this.page, this.count);
    }
  }

  setPagina(i:number, event:any) {
    event.preventDefault(); // evitar reload na tela
      this.page = i;
      this.findAll(this.page, this.count);
  }

  private showMessage(message: {type: string, text: string}) : void {
    this.menssage = message;
    this.buildClasses(message.type);
    setTimeout(()=>{
      this.menssage = undefined;
    }, 3000);
  }

  private buildClasses(type: string) : void {
    this.classCss = {
      'alert' : true
    }
    this.classCss['alert-'+type] = true;
  }

}
