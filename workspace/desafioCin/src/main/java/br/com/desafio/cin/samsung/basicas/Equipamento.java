package br.com.desafio.cin.samsung.basicas;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import br.com.desafio.cin.samsung.enums.TipoEquipamento;

@Entity
@Table(name = "Equipamento", schema = "cin")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id_equipamento", updatable = false, nullable = false)
	public Long id_equipamento;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "tipo é obrigatório")
	public TipoEquipamento tipo;

	@NotNull(message = "modelo é obrigatório")
	@NotBlank(message = "modelo é obrigatório")
	@NotEmpty(message = "modelo é obrigatório")
	@Column(name = "modelo")
	@Size(max = 255)
	public String modelo;

	@Column(columnDefinition = "mesano")
	@DateTimeFormat(pattern = "yyyy/MM")
	@NotNull(message = "mês ano é obrigatório")
	public String mesano;

	@Column(name = "valor")
	@NotNull(message = "valor é obrigatório")
	public BigDecimal valor;

	@Column(name = "foto")
	@NotNull(message = "foto é obrigatório")
	public File foto;

	@Transient
	public File qrcode;
	
	@Transient
	public String qrcodeConteudo;
	
	@Transient
	public String valorDepreciado;

	public Equipamento() {
	}

	public Equipamento(TipoEquipamento tipo, String modelo, String yearMonth, BigDecimal valor, File foto) {
		this.tipo = tipo;
		this.modelo = modelo;
		this.mesano = yearMonth;
		this.valor = valor;
		this.foto = foto;
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

	public File getFoto() {
		return foto;
	}

	public void setFoto(File foto) {
		this.foto = foto;
	}

	public File getQrcode() {
		return qrcode;
	}

	public void setQrcode(File qrcode) {
		this.qrcode = qrcode;
	}
	
	public String getQrcodeConteudo() {
		return qrcodeConteudo;
	}

	public void setQrcodeConteudo(String qrcodeConteudo) {
		this.qrcodeConteudo = qrcodeConteudo;
	}

	public String getValorDepreciado() {
		return valorDepreciado;
	}

	public void setValorDepreciado(String valorDepreciado) {
		this.valorDepreciado = valorDepreciado;
	}
}
