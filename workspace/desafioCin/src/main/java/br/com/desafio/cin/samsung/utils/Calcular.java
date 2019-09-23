package br.com.desafio.cin.samsung.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import br.com.desafio.cin.samsung.basicas.Equipamento;

public class Calcular {


	public static String calcularValorDepreciadoDoProduto(Equipamento equipamento, BigDecimal percentualDepreciacao) {
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
		if (diferencaMeses != 0) {
			BigDecimal valorDepreciado = equipamento.getValor().divide(new BigDecimal(diferencaMeses), 2, RoundingMode.HALF_EVEN);
			BigDecimal diferenca = equipamento.getValor().subtract(valorDepreciado);
			
			String diferencaFormatada = decimalFormat.format(diferenca);
			equipamento.setValorDepreciado(diferencaFormatada);
		} else {
			equipamento.setValorDepreciado("0");
		}
		
		return equipamento.getValorDepreciado();
	}
}
