package br.com.desafio.cin.samsung.email;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Envio {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void enviar(Mensagem mensagem) throws MessagingException, IOException {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setFrom(mensagem.getRemetente());
		sm.setTo(mensagem.getDestinatarios().toArray(
				new String[mensagem.getDestinatarios().size()]));
		sm.setSubject(mensagem.getAssunto());
		sm.setText(mensagem.getCorpo());
		javaMailSender.send(sm);
	}
	
	/*
	 * void sendEmailWithAttachment() throws MessagingException, IOException {
	 * 
	 * MimeMessage msg = javaMailSender.createMimeMessage(); MimeMessageHelper
	 * helper = new MimeMessageHelper(msg, true); helper.setTo("to_@email");
	 * helper.setSubject("Testing from Spring Boot");
	 * helper.setText("<h1>Check attachment for image!</h1>", true);
	 * helper.addAttachment("imagem.png", new ClassPathResource("imagem.png"));
	 * 
	 * javaMailSender.send(msg);
	 * 
	 * }
	 */
}
