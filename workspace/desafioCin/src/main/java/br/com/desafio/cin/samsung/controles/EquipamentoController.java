package br.com.desafio.cin.samsung.controles;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.controles.response.Response;
import br.com.desafio.cin.samsung.servicos.EquipamentoService;

@RestController
@RequestMapping("/api/equipamento")
@CrossOrigin(origins = "*")
//@PropertySource("${GOOGLE_APPLICATION_CREDENTIALS}")
public class EquipamentoController {

	@Autowired
	private EquipamentoService service;

	@PostMapping(value = "criar")
	public ResponseEntity<Response<Equipamento>> create(HttpServletRequest request,
			@RequestBody Equipamento equipamento, BindingResult result) {
		return this.service.createOrUpdate(equipamento, result);
	}

	@PutMapping(value = "atualizar")
	public ResponseEntity<Response<Equipamento>> update(HttpServletRequest request,
			@RequestBody Equipamento equipamento, BindingResult result) {
		return this.service.createOrUpdate(equipamento, result);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Equipamento>> findById(@PathVariable("id") String idEquipamento) throws JsonProcessingException {
		return this.service.findByIdEquipamento(new Long(idEquipamento));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String idEquipamento) {
		return this.service.delete(idEquipamento);
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Equipamento>>> findAll(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		return this.service.findAll(page, count);
	}
}
