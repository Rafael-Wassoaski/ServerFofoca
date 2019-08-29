package fofoca2;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;


public class Serrver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
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
