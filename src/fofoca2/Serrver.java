package fofoca2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;


public class Serrver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket cliente1 = new Socket();
		ThreadServer threadInicial = new ThreadServer(cliente1);
		
		try {
			threadInicial.enviar("RAFAEL; Meu nome aqui");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//server roda aqui
		 try (var listener = new ServerSocket(6668)) {
	            System.out.println("Servidor rodando");
	            var pool = Executors.newFixedThreadPool(20);
	            while (true) {
	                pool.execute(new ThreadServer(listener.accept()));
	            }
	        }catch (Exception e) {
				// TODO: handle exception
	        	System.out.println(e.getMessage());
			}

	}

}
