package br.com.desafio.cin.samsung.padraoProjeto;

import br.com.desafio.cin.samsung.utils.EquipamentoJson;
/**
 * Nesta classe fiz a demostração de 2 padões de projeto apenas
 * pra mostrar um pouco do conhecimento sobre padroes singleton e factory method
 */
public class FactoryMethod {

	private static FactoryMethod instance;

	public static FactoryMethod getInstance() {
		if (instance == null)
			instance = new FactoryMethod();
		return instance;
	}
	
	public Object criarObjetos(Object objeto) {
		
		if (objeto.equals(EquipamentoJson.class)) {
			return new EquipamentoJson();
		}
		
		return objeto;
	}

}
