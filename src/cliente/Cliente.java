package cliente;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {

	private Socket socket;
	private FileOutputStream fileOut;
	private BufferedOutputStream fileOut2;
	private InputStream input;
	private byte[] buffer;
	private int bytesRead;
	
	public Cliente(String url, int port, String arquivo) {
		conecta(url, port);
		recebeArquivo(arquivo);
		encerraConexoes();
	}

	private void conecta(String url, int port){
		try {
			socket = new Socket(url, port);
			input = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Conectado ao servidor.");
	}
	
	private void recebeArquivo(String arquivo){
		buffer = new byte[1024];
		arquivo = "/home/maykon/testeTransmissao/"+arquivo;
		try {
			fileOut = new FileOutputStream(arquivo);
			fileOut2 = new BufferedOutputStream(fileOut);
			while((bytesRead = input.read(buffer)) != -1){
				fileOut2.write(buffer, 0, bytesRead);
				fileOut2.flush();
				System.out.println("Recebendo...");
			}
			System.out.println("Arquivo recebido.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void encerraConexoes(){
		try {
			input.close();
			fileOut2.close();
			socket.close();
			System.out.println("Conexões encerradas.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Cliente("10.42.0.30", 3377, "phpStorm.tar.gz");
	}
}
