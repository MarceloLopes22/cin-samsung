import { Component, OnInit } from '@angular/core';
import { Equipamento } from 'src/app/model/equipamento.model';
import { EquipamentoService } from 'src/app/services/equipamento.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseApi } from 'src/app/model/responseApi.model';

@Component({
  selector: 'app-equipamento-detalhe',
  templateUrl: './equipamento-detalhe.component.html'
})
export class EquipamentoDetalheComponent implements OnInit {

  equipamento = new Equipamento(null, null, '', '', 0, null, null, '');
  menssage: {};
  classCss: {};

  constructor(private equipamentoService: EquipamentoService,
              private route: ActivatedRoute,
              private router: Router
    ) {
   }

  ngOnInit() {
    let id:number = this.route.snapshot.params['id'];
    if(id != undefined) {
      this.equipamentoService.findById(id).subscribe((responseApi:ResponseApi) =>{
        this.equipamento = responseApi.data;
      }, err =>{
        this.showMessage({
          type: 'error',
          text: err['error']['errors'][0]
        });
      });
    }
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

  voltar(){
    this.router.navigate(['/equipamento-lista'])
  }


}
