package br.com.desafio.cin.samsung.basicas;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.swing.ImageIcon;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import br.com.desafio.cin.samsung.enums.TipoEquipamento;

@Entity
@Table(name = "Equipamento", schema = "cin")
public class Equipamento {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id_equipamento", updatable = false, nullable = false)
	private Long id_equipamento;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "tipo é obrigatório")
	private TipoEquipamento tipo;

	@NotNull(message = "modelo é obrigatório")
	@NotBlank(message = "modelo é obrigatório")
	@NotEmpty(message = "modelo é obrigatório")
	@Column(name = "modelo")
	@Size(max = 255)
	private String modelo;

	@Column(columnDefinition = "mesano")
	@NotNull(message = "mês ano é obrigatório")
	private String mesano;

	@Column(name = "valor")
	@NotNull(message = "valor é obrigatório")
	private Double valor;

	@Column(name = "foto")
	@NotNull(message = "foto é obrigatório")
	private File foto;

	@Transient
	private ImageIcon qrcode;

	public Equipamento() {
	}

	public Equipamento(TipoEquipamento tipo, String modelo, String yearMonth, Double valor, File foto) {
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public File getFoto() {
		return foto;
	}

	public void setFoto(File foto) {
		this.foto = foto;
	}

	public ImageIcon getQrcode() {
		return qrcode;
	}

	public void setQrcode(ImageIcon qrcode) {
		this.qrcode = qrcode;
	}

}
