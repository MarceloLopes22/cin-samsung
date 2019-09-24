import { TipoEquipamento } from './enums/TipoEquipamento';

export class Equipamento {

    constructor(public id_equipamento:number,
                public tipo:TipoEquipamento,
                public modelo: string,
                public mesano: string,
                public valor: number,
                public foto: any,
                public qrcode: any,
                public qrcodeConteudo: string,
                public valorDepreciado: string){}
}