package br.com.desafio.cin.samsung.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class QrCode {


	public static FileOutputStream criarQRCode(String texto, int tamanho) throws IOException {
		
		//URL systemResource = ClassLoader.getSystemResource("./../../../resources/img/imagem.png");
		File file = new File("imagem.png");
		FileOutputStream qrcode = new FileOutputStream(file);
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
	
	public static byte[] filetoByteArray(String path) {
	    byte[] data;
	    try {
	        InputStream input = new FileInputStream(path);
	        int byteReads;
	        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
	        while ((byteReads = input.read()) != -1) {
	            output.write(byteReads);
	        }

	        data = output.toByteArray();
	        output.close();
	        input.close();
	        return data;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
}
