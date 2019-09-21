package br.com.desafio.cin.samsung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "br.com.desafio.cin.samsung.*")
public class DesafioCinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioCinApplication.class, args);

		/*
		 * AnnotationConfigApplicationContext context = new
		 * AnnotationConfigApplicationContext(
		 * DesafioCinApplication.class.getPackage().getName() );
		 */
		/*
		 * Envio envio = new Envio(); try { envio.enviar(new
		 * Mensagem("Marcelo Lopes <marcelomlopes2@gmail.com>",
		 * Arrays.asList("Marcelo Lopes <marcelomlopes2@gmail.com>"),
		 * "Aula Spring Email", "Olá o envio deu certo.")); } catch (MessagingException
		 * e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * //context.close(); System.out.println("Fim!!");
		 */
	}

}
