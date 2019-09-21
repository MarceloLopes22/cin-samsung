package br.com.desafio.cin.samsung.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QrCode {


	public static FileOutputStream criarQRCode(String texto, int tamanho) throws IOException {
		
		//URL systemResource = ClassLoader.getSystemResource("./../../../resources/img/imagem.png");
		FileOutputStream qrcode = new FileOutputStream(new File("imagem.png"));
		ByteArrayOutputStream arrayOutputStream = QRCode.from(texto).to(ImageType.PNG).withSize(tamanho, tamanho)
				.stream();
		qrcode.write(arrayOutputStream.toByteArray());
		qrcode.flush();
		qrcode.close();
		return qrcode;
	}
	
	public static void removerImagensDiretorio() {
		File file = new File("imagem.png");
		if (file.exists()) {
			file.delete();
		}
	}
}
