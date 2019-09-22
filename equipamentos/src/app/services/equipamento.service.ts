import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Equipamento } from '../model/equipamento.model';
import { EQUIPAMENTO_API } from './equipamento.api';

@Injectable()
export class EquipamentoService {

  constructor(private http: HttpClient) { }

  createOrUpdate(equipamento: Equipamento) {
    if(equipamento.id_equipamento != null && equipamento.id_equipamento > 0) {
      return this.http.put(`${EQUIPAMENTO_API}/api/equipamento/criar`, equipamento);
    }
    return this.http.post(`${EQUIPAMENTO_API}/api/equipamento/atualizar`, equipamento);
  }

  findAll(page:number, count: number) {
    return this.http.get(`${EQUIPAMENTO_API}/api/equipamento/${page}/${count}`);
  }

  findById(id:number) {
    return this.http.get(`${EQUIPAMENTO_API}/api/equipamento/${id}`);
  }
  
  delete(id:number) {
    return this.http.delete(`${EQUIPAMENTO_API}/api/equipamento/${id}`);
  }
}
