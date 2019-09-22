package br.com.desafio.cin.samsung.enums;

public enum TipoEquipamento {

	ELETRONICO("ELETRONICO"),
    DOMESTICO("DOMESTICO"),
    AUTOMOTIVO("AUTOMOTIVO"),
    CONSTRUCAO("CONSTRUCAO");
	
	private String nome;
	
	private TipoEquipamento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public static TipoEquipamento getByName(String nome) {
		for (TipoEquipamento type : TipoEquipamento.values()) {
            if (type.getNome().equals(nome)) {
                return type;
            }
        }
        return null;
	}
}
