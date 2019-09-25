package br.com.desafio.cin.samsung.servicos;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.controles.response.Response;

public interface EquipamentoService {

	public ResponseEntity<Response<Equipamento>> createOrUpdate(Equipamento equipamento,  BindingResult result);

	public ResponseEntity<Response<Equipamento>> findByIdEquipamento(Long idEquipamento);

	public ResponseEntity<Response<String>> delete(String idEquipamento);

	public ResponseEntity<Response<Page<Equipamento>>> findAll(int page, int count);

}
