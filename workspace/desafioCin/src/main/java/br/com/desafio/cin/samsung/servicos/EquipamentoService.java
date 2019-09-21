package br.com.desafio.cin.samsung.servicos;

import org.springframework.data.domain.Page;

import br.com.desafio.cin.samsung.basicas.Equipamento;

public interface EquipamentoService {

	public Equipamento createOrUpdate(Equipamento equipamento);

	public Equipamento findByIdEquipamento(Long idEquipamento);

	public void delete(Long idEquipamento);

	public Page<Equipamento> findAll(int page, int count);

}
