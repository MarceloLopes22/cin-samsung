package br.com.desafio.cin.samsung.utils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import br.com.desafio.cin.samsung.basicas.Equipamento;

public class Calcular {


	public static String calcularValorDepreciadoDoProduto(Equipamento equipamento, Double percentualDepreciacao) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

		//Dados da base
		String mesano = equipamento.getMesano();
		String diaFixoEstipulado = "-01";
		String datainformada = mesano.concat(diaFixoEstipulado);
		
		LocalDate dataInformada = LocalDate.parse(datainformada, formatter);
		LocalDate dataAtual = LocalDate.now();
		
		//Calculo da diferença entre as 2 datas em meses
		long diferencaMeses = ChronoUnit.MONTHS.between(dataInformada,dataAtual);  
		Double valorDepreciado = equipamento.getValor() / diferencaMeses;
		double diferenca = equipamento.getValor() - valorDepreciado;
		
		String diferencaFormatada = decimalFormat.format(diferenca);
		equipamento.setValorDepreciado(diferencaFormatada);
		
		return equipamento.getValorDepreciado();
	}
}
