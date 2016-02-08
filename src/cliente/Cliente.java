package cliente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente {

	private Socket socket;
	private FileOutputStream fileOut;
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
		arquivo = "/tmp/"+arquivo;
		try {
			fileOut = new FileOutputStream(arquivo);
			while((bytesRead = input.read(buffer)) != -1){
				fileOut.write(buffer, 0, bytesRead);
				fileOut.flush();
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
			fileOut.close();
			socket.close();
			System.out.println("Conexões encerradas.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Cliente("localhost", 3377, "jperf-2.0.0.zip");
	}
}
