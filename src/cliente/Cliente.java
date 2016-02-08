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
	
	public Cliente() {
		try {
			socket = new Socket("localhost", 3377);
			input = socket.getInputStream();
			System.out.println("Conectado ao servidor.");
			
			fileOut = new FileOutputStream("/tmp/novoarquivo.zip");
			
			buffer = new byte[1024];
			
			while((bytesRead = input.read(buffer)) != -1){
				fileOut.write(buffer, 0, bytesRead);
				fileOut.flush();
				System.out.println("Recebendo...");
			}
			
			System.out.println("Arquivo recebido.");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socket != null){
				try {
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(fileOut != null){
				try {
					fileOut.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args){
		new Cliente();
	}
}
