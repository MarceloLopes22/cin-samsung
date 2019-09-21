package br.com.desafio.cin.samsung.basicas;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.desafio.cin.samsung.enums.TipoEquipamento;

@Entity
@Table(name = "Equipamento", schema = "cin")
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
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
	@DateTimeFormat(pattern = "MM/yyyy")
	@NotNull(message = "mês ano é obrigatório")
	private LocalDate mesano;

	@Column(name = "valor")
	@NotNull(message = "valor é obrigatório")
	private Double valor;

	@Column(name = "foto")
	@NotNull(message = "foto é obrigatório")
	private File foto;

	/*
	 * @Column(name = "qrcode") private File qrcode;
	 */

	public Equipamento() {
	}

	public Equipamento(TipoEquipamento tipo, String modelo, LocalDate mesano, Double valor, File foto) {
		this.tipo = tipo;
		this.modelo = modelo;
		this.mesano = mesano;
		this.valor = valor;
		this.foto = foto;
	}

	public Long getIdEquipamento() {
		return id_equipamento;
	}

	public void setIdEquipamento(Long idEquipamento) {
		this.id_equipamento = idEquipamento;
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

	public LocalDate getMesano() {
		return mesano;
	}

	public void setMesano(LocalDate mesano) {
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
	/*
	 * public File getQrcode() { return qrcode; }
	 * 
	 * public void setQrcode(File qrcode) { this.qrcode = qrcode; }
	 */
}
