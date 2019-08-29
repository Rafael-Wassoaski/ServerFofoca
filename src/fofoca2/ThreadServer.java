package fofoca2;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class ThreadServer implements Runnable{
	
	private List<String> ip = new ArrayList<String>();
	
	private Socket cliente;
	public ThreadServer(Socket cliente) {
		
	this.cliente = cliente;
		
		
	}
	
	private void pegarIps() {
		try {
			String ipAddress = null;
			  Enumeration<NetworkInterface> net = null;
			  net = NetworkInterface.getNetworkInterfaces();
			  
			  while (net.hasMoreElements()) {
		            NetworkInterface element = net.nextElement();
		            Enumeration<InetAddress> addresses = element.getInetAddresses();
		            while (addresses.hasMoreElements()) {
		                InetAddress ip = addresses.nextElement();

		                if (ip.isSiteLocalAddress()) {
		                    ipAddress = ip.getHostAddress().toString();
		                }           
		            }
		        }
		       
			  
			 
			
			  String[] teuIp = ipAddress.split("[.]");
			  
			  
			  
			  

			  InetAddress inet;
			  for(int i =Integer.parseInt(teuIp[3]) - 1; i > 0; i--) {
				  inet = InetAddress.getByName(String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
				  if(inet.isReachable(500)) {
					  ip.add(String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
					  System.out.println("Seu vizinho de baixo: "+String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
					  break;
				  }
			  }
			  
			  
			  for(int i =Integer.parseInt(teuIp[3]) + 1; i < 256; i++) {
				  inet = InetAddress.getByName(String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
				  if(inet.isReachable(500)) {
					  ip.add(String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
					  System.out.println("Seu vizinho de cima: "+String.format("%s.%s.%s.%d", teuIp[0], teuIp[1], teuIp[2], i));
					  break;
				  }
			  }
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
		Scanner leitor = new Scanner(cliente.getInputStream());
		
		
		String fofoca = leitor.nextLine();
		
		System.out.println("Fofoca: "+fofoca);
		
		
		
		pegarIps();
		
		if(ip.isEmpty()) {
			System.out.println("Sem vizinhos, não posso mandar a fofoca");
		}else {
			String[] fofocaFrag = fofoca.split(";");
			OutputStream output;
			PrintWriter pr;
			
			
			Socket envio;
			
			for(String ipEnvio : ip) {
				
				if(cliente.getInetAddress() != ip) {
					envio = new Socket(ipEnvio, 6668);
					output = envio.getOutputStream();
					pr = new PrintWriter(output, true);
					pr.println("RAFAEL;"+fofocaFrag[1]);
				}
			}

		}
		
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
