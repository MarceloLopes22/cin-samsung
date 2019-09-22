package br.com.desafio.cin.samsung.controles;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.desafio.cin.samsung.basicas.Equipamento;
import br.com.desafio.cin.samsung.constantes.Constantes;
import br.com.desafio.cin.samsung.controles.response.Response;
import br.com.desafio.cin.samsung.servicos.EquipamentoService;
import br.com.desafio.cin.samsung.utils.Calcular;
import br.com.desafio.cin.samsung.utils.EquipamentoJson;
import br.com.desafio.cin.samsung.utils.QrCode;

@RestController
@RequestMapping("/api/equipamento")
@CrossOrigin(origins = "*")
public class EquipamentoController {

	@Autowired
	private EquipamentoService equipamentoService;

	@Autowired
	private ObjectMapper mapper;

	@Autowired(required = true)
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;
	/*
	 * @Autowired(required = true) private Session session;
	 */

	/*
	 * @Bean public void setMailSender(JavaMailSender mailSender) { this.mailSender
	 * = mailSender; }
	 */
	 

	@PostMapping(value = "criar")
	public ResponseEntity<Response<Equipamento>> create(HttpServletRequest request,
			@RequestBody Equipamento equipamento, BindingResult result) {
		Response<Equipamento> response = new Response<Equipamento>();

		QrCode.removerImagensDiretorio();

		try {
			validateComun(equipamento, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			equipamento.setFoto(equipamento.getFoto().getAbsoluteFile());
			Equipamento newEquipamento = equipamentoService.createOrUpdate(equipamento);
			gerarQrCode(newEquipamento);
			// ImageIcon qrCode = QrCode.lerImagem(new File(Constantes.QRCODE_PATH));
			newEquipamento.setQrcode(new File(Constantes.QRCODE_PATH));
			//this.sendEmail(newEquipamento.getQrcode());
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
		EquipamentoJson json = new EquipamentoJson();
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

		if (equipamento.getValor() == null || equipamento.getValor().equals(Double.MIN_VALUE)) {
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

		QrCode.removerImagensDiretorio();

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
			//this.sendEmail(equipamentoUpdated.getQrcode());
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
	public ResponseEntity<Response<Equipamento>> findById(@PathVariable("id") String idEquipamento) {
		Response<Equipamento> response = new Response<Equipamento>();

		Optional<Equipamento> equipamento = equipamentoService.findByIdEquipamento(new Long(idEquipamento));

		if (equipamento == null || !equipamento.isPresent()) {
			response.getErros().add("Equipamento não encontrado. IdEquipamento = " + idEquipamento);
			return ResponseEntity.badRequest().body(response);
		}
		Equipamento equipamentoConsultado = equipamento.get();
		Calcular.calcularValorDepreciadoDoProduto(equipamentoConsultado, new Double(env.getProperty("depreciacao")));
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

	private void sendEmail(File arquivo) {
		try {
			Properties properties = getProp();
			FileSystemResource file = new FileSystemResource(arquivo.getCanonicalPath());
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(env.getProperty("spring.mail.username"));
			helper.setFrom(env.getProperty("spring.mail.username"), "Marcelo Lopes");
			helper.setText("Enviando arquivo em anexo!!!", false);
			helper.setValidateAddresses(true);
			helper.setSubject("Teste Spring com Anexo");
			// Descricao do Anexo, arquivo anexo
			helper.addAttachment("qrCode.png", file);
			/*
			 * session = Session.getDefaultInstance(properties, new
			 * javax.mail.Authenticator() { protected PasswordAuthentication
			 * getPasswordAuthentication() { return new
			 * PasswordAuthentication(env.getProperty("spring.mail.username"),
			 * env.getProperty("spring.mail.password")); } });
			 */

			//Transport transport = session.getTransport();
			InternetAddress addressFrom = new InternetAddress(env.getProperty("spring.mail.username"));
			// helper.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(env.getProperty("spring.mail.username")));

			//transport.connect();
			mailSender.send(mimeMessage);
			//transport.close();
			System.out.println("Envio com Sucesso.");
		} catch (MailException e) {
			System.out.println("Nao foi possivel realizar o envio com anexo.\n" + e.getMessage());
		} catch (MessagingException e) {
			System.out.println("Email nao pode ser eviado.\n" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Anexo nao encontrado\n" + e.getMessage());
		}

		// cria o anexo.
		// EmailAttachment attachment = new EmailAttachment();
		// attachment.setPath("mypictures/john.jpg"); //caminho da imagem
//		attachment.setDisposition(EmailAttachment.ATTACHMENT);
//		attachment.setDescription("Picture of John");
//		attachment.setName("John");

		// Cria a mensagem de e-mail.
//		MultiPartEmail email = new MultiPartEmail();
//		email.setHostName("mail.myserver.com"); // o servidor SMTP para envio do e-mail
//		email.addTo("jdoe@somewhere.org", "John Doe"); //destinatario
//		email.setFrom("me@apache.org", "Me"); //remetente
//		email.setSubject("Mensagem de teste com anexo"); //Assunto
//		email.setMsg("Aqui está a Imagem anexada ao e-mail"); //conteudo do e-mail
//		email.attach(attachment); // adiciona o anexo à mensagem

//		email.send();// envia o e-mail
	}

	private Properties getProp() throws IOException {
		Properties props = new Properties();
		props.setProperty(env.getProperty("spring.mail.host"), "smtp.gmail.com");
		props.setProperty(env.getProperty("spring.mail.properties.mail.smtp.auth"), "true");
		props.setProperty(env.getProperty("spring.mail.port"), "587");
		props.setProperty(env.getProperty("spring.mail.properties.mail.smtp.starttls.enable"), "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// FileInputStream file = new FileInputStream(new
		// File("/main/resources/application.properties"));
		// props.load(file);
		return props;
	}

}
