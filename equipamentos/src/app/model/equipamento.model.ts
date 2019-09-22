import { TipoEquipamentoEnum } from './enums/TipoEquipamentoEnum';

export class Equipamento {

    constructor(public id_equipamento:number,
                public tipoEquipamentoEnum:TipoEquipamentoEnum,
                public modelo: string,
                public mesano: string,
                public valor: number,
                public foto: File,
                public qrcode: any){}
}