package br.com.desafio.cin.samsung.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.cin.samsung.basicas.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
	
}
