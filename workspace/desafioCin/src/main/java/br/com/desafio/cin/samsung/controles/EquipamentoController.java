package br.com.desafio.cin.samsung.controles;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.constantes.Constantes;
import br.com.desafio.cin.samsung.controles.response.Response;
import br.com.desafio.cin.samsung.email.EnviarEmail;
import br.com.desafio.cin.samsung.padraoProjeto.FactoryMethod;
import br.com.desafio.cin.samsung.servicos.EquipamentoService;
import br.com.desafio.cin.samsung.utils.Calcular;
import br.com.desafio.cin.samsung.utils.EquipamentoJson;
import br.com.desafio.cin.samsung.utils.QrCode;

@RestController
@RequestMapping("/api/equipamento")
@CrossOrigin(origins = "*")
//@PropertySource("${GOOGLE_APPLICATION_CREDENTIALS}")
public class EquipamentoController {

	@Autowired
	private EquipamentoService equipamentoService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Environment env;
	
	@Autowired
	private EnviarEmail email;
	 

	@PostMapping(value = "criar")
	public ResponseEntity<Response<Equipamento>> create(HttpServletRequest request,
			@RequestBody Equipamento equipamento, BindingResult result) {
		Response<Equipamento> response = new Response<Equipamento>();

		try {
			validateComun(equipamento, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			equipamento.setFoto(equipamento.getFoto().getAbsoluteFile());
			Equipamento newEquipamento = equipamentoService.createOrUpdate(equipamento);
			gerarQrCode(newEquipamento);
			newEquipamento.setQrcode(new File(Constantes.QRCODE_PATH));
			newEquipamento.setQrcodeConteudo(mapper.writeValueAsString(this.parseEquipamentoToEquipamentoJson(newEquipamento)));
			email.enviar(newEquipamento.getQrcode());
			response.setData(newEquipamento);

		} catch (Exception e) {
			response.getErros().add(e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	private void gerarQrCode(Equipamento equipamento) {
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		int tamanho = 125;
		try {
			EquipamentoJson toEquipamentoJson = parseEquipamentoToEquipamentoJson(equipamento);
			QrCode.criarQRCode(mapper.writeValueAsString(toEquipamentoJson), tamanho);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private EquipamentoJson parseEquipamentoToEquipamentoJson(Equipamento equipamento) {
		EquipamentoJson json = (EquipamentoJson) FactoryMethod.getInstance().criarObjetos(EquipamentoJson.class);
		json.setId_equipamento(equipamento.getId_equipamento());
		json.setMesano(equipamento.getMesano());
		json.setModelo(equipamento.getModelo());
		json.setTipo(equipamento.getTipo());
		json.setValor(equipamento.getValor());

		return json;
	}

	private void validateComun(Equipamento equipamento, BindingResult result) {

		if (equipamento.getTipo() == null) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Tipo é obrigatório."));
		}

		if (equipamento.getModelo() == null || "".equals(equipamento.getModelo())) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Modelo é obrigatório."));
		}

		if (equipamento.getMesano() == null) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Mês ano é obrigatório."));
		}

		if (equipamento.getValor() == null || equipamento.getValor().equals(BigDecimal.ZERO)) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Valor é obrigatório."));
		}

		if (equipamento.getFoto() == null) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Foto é obrigatório."));
		}

	}

	@PutMapping(value = "atualizar")
	public ResponseEntity<Response<Equipamento>> update(HttpServletRequest request,
			@RequestBody Equipamento equipamento, BindingResult result) {
		Response<Equipamento> Response = new Response<Equipamento>();

		try {
			validateUpdate(equipamento, result);
			validateComun(equipamento, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erro -> Response.getErros().add(erro.getDefaultMessage()));
				return ResponseEntity.badRequest().body(Response);
			}

			Equipamento equipamentoUpdated = equipamentoService.createOrUpdate(equipamento);
			gerarQrCode(equipamentoUpdated);
			equipamentoUpdated.setQrcode(new File(Constantes.QRCODE_PATH));
			email.enviar(equipamentoUpdated.getQrcode());
			Response.setData(equipamentoUpdated);

		} catch (Exception e) {
			Response.getErros().add(e.getMessage());
			return ResponseEntity.badRequest().body(Response);
		}
		return ResponseEntity.ok(Response);
	}

	private void validateUpdate(Equipamento Equipamento, BindingResult result) {

		if (Equipamento.getId_equipamento() == null) {
			result.addError(new ObjectError(Equipamento.class.getSimpleName(), "Id Equipamento é obrigatório."));
		}
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<Response<Equipamento>> findById(@PathVariable("id") String idEquipamento) throws JsonProcessingException {
		Response<Equipamento> response = new Response<Equipamento>();

		Optional<Equipamento> equipamento = equipamentoService.findByIdEquipamento(new Long(idEquipamento));

		if (equipamento == null || !equipamento.isPresent()) {
			response.getErros().add("Equipamento não encontrado. IdEquipamento = " + idEquipamento);
			return ResponseEntity.badRequest().body(response);
		}
		Equipamento equipamentoConsultado = equipamento.get();
		equipamentoConsultado.setQrcodeConteudo(mapper.writeValueAsString(this.parseEquipamentoToEquipamentoJson(equipamentoConsultado)));
		equipamentoConsultado.setQrcode(new File(Constantes.QRCODE_PATH));
		Calcular.calcularValorDepreciadoDoProduto(equipamentoConsultado, new BigDecimal(env.getProperty("depreciacao")));
		response.setData(equipamentoConsultado);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String idEquipamento) {
		Response<String> response = new Response<String>();

		if (idEquipamento == null || "".equalsIgnoreCase(idEquipamento) || "null".equalsIgnoreCase(idEquipamento)) {
			response.getErros().add("IdEquipamento = " + idEquipamento + " não pode ser nulo.");
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Equipamento> equipamento = equipamentoService.findByIdEquipamento(new Long(idEquipamento));

		if (equipamento == null || !equipamento.isPresent()) {
			response.getErros().add("Equipamento não encontrado. IdEquipamento = " + idEquipamento);
			return ResponseEntity.badRequest().body(response);
		}

		equipamentoService.delete(new Long(idEquipamento));

		return ResponseEntity.ok(new Response<String>());
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Equipamento>>> findAll(@PathVariable("page") int page,
			@PathVariable("count") int count) {
		Response<Page<Equipamento>> response = new Response<Page<Equipamento>>();

		if (page == 0 && count == 0) {
			response.getErros().add("Falha na paginação");
			return ResponseEntity.badRequest().body(response);
		}

		Page<Equipamento> equipamento = equipamentoService.findAll(page, count);
		response.setData(equipamento);
		return ResponseEntity.ok().body(response);

	}
}
