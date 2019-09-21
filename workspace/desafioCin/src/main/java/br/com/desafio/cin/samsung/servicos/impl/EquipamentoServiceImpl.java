package br.com.desafio.cin.samsung.servicos.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.repositorios.EquipamentoRepository;
import br.com.desafio.cin.samsung.servicos.EquipamentoService;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {
	
	@Autowired
	private EquipamentoRepository repo;

	@Override
	public Equipamento createOrUpdate(Equipamento equipamento) {
		return this.repo.save(equipamento);
	}

	@Override
	public Equipamento findByIdEquipamento(Long idEquipamento) {
		return this.repo.findById(idEquipamento).get();
	}

	@Override
	public void delete(Long idEquipamento) {
		this.repo.deleteById(idEquipamento);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page<Equipamento> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		Page<Equipamento> lista = this.repo.findAll(pages);
		return lista;
	}

	

}
