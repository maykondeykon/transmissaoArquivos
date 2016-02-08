package servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Socket socket;
	private OutputStream socketOut;
	private ServerSocket servSocket;
	private FileInputStream fileIn;
	private byte[] buffer;
	private int bytesRead;
	private File file;
	
	public Servidor(String arquivo) {
		ativaServidor();
		carregaArquivo(arquivo);
		enviaArquivo();
		encerraConexoes();
	}
	
	private void ativaServidor(){
		try {
			servSocket = new ServerSocket(3377);
			System.out.println("Servidor aguardando conexões na porta 3377.");
			socket = servSocket.accept();
			System.out.println("Conexão iniciada.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void carregaArquivo(String arquivo){
		buffer = new byte[1024];
		file = new File(arquivo);
		try {
			fileIn = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void enviaArquivo(){
		try {
			socketOut = socket.getOutputStream();
			while((bytesRead = fileIn.read(buffer))!= -1){
				socketOut.write(buffer, 0, bytesRead);
				socketOut.flush();
				System.out.println("Enviando...");
			}
			System.out.println("Arquivo enviado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void encerraConexoes(){
		try {
			fileIn.close();
			socketOut.close();
			socket.close();
			servSocket.close();
			System.out.println("Conexões encerradas.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Servidor("/home/maykon/Downloads/jperf-2.0.0.zip");
	}
}