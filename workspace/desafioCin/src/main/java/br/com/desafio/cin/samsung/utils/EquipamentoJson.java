package br.com.desafio.cin.samsung.utils;

import java.math.BigDecimal;

import br.com.desafio.cin.samsung.enums.TipoEquipamento;

public class EquipamentoJson {

	public Long id_equipamento;
	public TipoEquipamento tipo;
	public String modelo;
	public String mesano;
	public BigDecimal valor;
	
	public EquipamentoJson() {
	}
	
	public EquipamentoJson(Long id_equipamento, TipoEquipamento tipo, String modelo, String mesano, BigDecimal valor) {
		this.id_equipamento = id_equipamento;
		this.tipo = tipo;
		this.modelo = modelo;
		this.mesano = mesano;
		this.valor = valor;
	}

	public Long getId_equipamento() {
		return id_equipamento;
	}

	public void setId_equipamento(Long id_equipamento) {
		this.id_equipamento = id_equipamento;
	}

	public TipoEquipamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMesano() {
		return mesano;
	}

	public void setMesano(String mesano) {
		this.mesano = mesano;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipamentoJson other = (EquipamentoJson) obj;
		if (id_equipamento == null) {
			if (other.id_equipamento != null)
				return false;
		} else if (!id_equipamento.equals(other.id_equipamento))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
}
