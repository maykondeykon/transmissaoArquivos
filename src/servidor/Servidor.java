package servidor;

import java.io.File;
import java.io.FileInputStream;
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
	
	public Servidor() {
		try {
			servSocket = new ServerSocket(3377);
			System.out.println("Servidor aguardando conexões.");
			socket = servSocket.accept();
			
			buffer = new byte[1024];
			file = new File("/home/maykon/Downloads/jperf-2.0.0.zip");
			fileIn = new FileInputStream(file);
			
			socketOut = socket.getOutputStream();
			
			while((bytesRead = fileIn.read(buffer))!= -1){
				socketOut.write(buffer, 0, bytesRead);
				socketOut.flush();
				System.out.println("Enviando...");
			}
			
			System.out.println("Arquivo enviado.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(socketOut != null){
				try {
					socketOut.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(servSocket != null){
				try {
					servSocket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(fileIn != null){
				try {
					fileIn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args){
		new Servidor();
	}
}
