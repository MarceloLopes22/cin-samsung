package br.com.desafio.cin.samsung.email;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		
		senderImpl.setHost(env.getProperty("spring.mail.host"));
		senderImpl.setPort(env.getProperty("spring.mail.port", Integer.class));
		senderImpl.setUsername(env.getProperty("spring.mail.username"));
		senderImpl.setPassword(env.getProperty("spring.mail.password"));
		
		Properties properties = new Properties();
		/*
		 * properties.put("mail.transport.protocol", "smtp");
		 * properties.put("mail.smtp.auth", true);
		 * properties.put("mail.smtp.starttls.enable", true);
		 * properties.put("mail.smtp.connectiontimeout", 10000);
		 */
		properties.put("spring.mail.protocol", "smtp");
		properties.put("spring.mail.properties.mail.smtp.auth", true);
		properties.put("spring.mail.properties.mail.smtp.starttls.enable", true);
		properties.put("spring.mail.properties.mail.smtp.connectiontimeout", 5000);
		properties.put("spring.mail.properties.mail.smtp.timeout", 5000);
		properties.put("spring.mail.properties.mail.smtp.writetimeout", 5000);
		
		senderImpl.setJavaMailProperties(properties);
		
		return senderImpl;
	}
}
