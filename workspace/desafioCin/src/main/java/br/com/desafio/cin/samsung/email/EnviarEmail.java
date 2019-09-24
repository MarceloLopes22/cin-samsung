package br.com.desafio.cin.samsung.email;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.com.desafio.cin.samsung.constantes.Constantes;

@Service
public class EnviarEmail {

	@Autowired
	private Environment env;

	public void enviar(File file) throws NoSuchProviderException, AddressException {
		
		//Informações gerais do email
		String remetente = env.getProperty(Constantes.EMAIL_DESTINO);
		String titulo = "Enviando Arquivo pela aplicação.";
		String msg = "Segue arquivo em anexo.";
		final String emissor = env.getProperty(Constantes.USERNAME);
		final String senha = env.getProperty(Constantes.PASSWORD);
		
		//Configuração das propriedades
		Properties props = configurarPropriedades();
		
		//Sesão
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emissor, senha);
			}
		});

		//Envio do email.
		try {
			 // Crie um objeto MimeMessage padrão.
	         Message message = new MimeMessage(session);

	         // Definir de: campo de cabeçalho do cabeçalho.
	         message.setFrom(new InternetAddress(emissor));

	         // Definir para: campo de cabeçalho do cabeçalho.
	         message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(remetente));

	         // Definir Assunto: campo de cabeçalho
	         message.setSubject(titulo);

	         // Crie a parte da mensagem
	         BodyPart corporEmail = new MimeBodyPart();

	         // Agora defina a mensagem real
	         corporEmail.setText(msg);

	         // Crie uma mensagem com várias partes
	         Multipart multipart = new MimeMultipart();

	         // Definir parte da mensagem de texto
	         multipart.addBodyPart(corporEmail);

	         // A parte dois é o anexo
	         corporEmail = new MimeBodyPart();
	         DataSource source = new FileDataSource(file);
	         corporEmail.setDataHandler(new DataHandler(source));
	         corporEmail.setFileName(file.getName());
	         multipart.addBodyPart(corporEmail);

	         // Junta as partes completas da mensagem
	         message.setContent(multipart);

	         // Envia a mensagem
	         Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}

	private Properties configurarPropriedades() {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", env.getProperty(Constantes.PROTOCOL));
		props.setProperty("mail.host", env.getProperty(Constantes.HOST));
		props.put("mail.smtp.auth", env.getProperty(Constantes.AUTH));
		props.put("mail.smtp.port", env.getProperty(Constantes.PORT));
		props.put("mail.debug", "true");
		
		props.put("mail.smtp.socketFactory.port", env.getProperty(Constantes.SOCKET_PORT));
		props.put("mail.smtp.socketFactory.class", env.getProperty(Constantes.SOCKET_CLASS));
		props.put("mail.smtp.socketFactory.fallback", env.getProperty(Constantes.SOCKET_FALLBACK));
		
		props.put("mail.smtp.connectiontimeout", env.getProperty(Constantes.CONNECTION_TIMEOUT));
		props.put("mail.smtp.timeout", env.getProperty(Constantes.TIMEOUT));
		props.put("mail.smtp.writetimeout", env.getProperty(Constantes.WRITE_TIMEOUT));
		return props;
	}

}
