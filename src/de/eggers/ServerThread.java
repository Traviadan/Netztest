package de.eggers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread{
    private ServerSocket server;
    private int port;
    private List<ConnectionThread> clients = new ArrayList<ConnectionThread>();
    public boolean stopRun;
    
    public ServerThread(int port) {
    	this.port = port;
    	this.open();
    }
    
    
    public void run() {
        if(this.server == null) return;
    	while(true) {
            Socket client;
			try {
				client = this.server.accept();
	            String clientAddress = client.getInetAddress().getHostAddress();
	            System.out.println("\r\nNew connection from " + clientAddress);
	            ConnectionThread ct = new ConnectionThread(client);
	            this.clients.add(ct);
	            ct.start();
			} catch (SocketTimeoutException e) {
				if(this.stopRun) {
					try {
						this.server.close();
					} catch (IOException e1) {
						System.out.println("Fehler beim schließen des Sockets: " + e.getMessage());
					}
					break;
				}
			} catch (IOException e) {
				System.out.println("Fehler beim warten auf Verbindung: " + e.getMessage());
				break;
			}
    	}
    }

    private void open() {
    	try{
    		this.server = new ServerSocket(this.port);
    		this.server.setSoTimeout(500);
    	} catch(IOException e) {
    		System.out.println("Fehler beim öffnen des ServersSockets: " + e.getMessage());
    	}
    }
    
    public InetAddress getSocketAddress() {
    	if(this.server != null) {
    		return this.server.getInetAddress();
    	} else {
    		return null;
    	}
    }
    
    public int getPort() {
        if(this.server != null) {
        	return this.server.getLocalPort();
        } else {
        	return 0;
        }
    }
    
    public List<ConnectionThread> getClients() {
    	return this.clients;
    }
}
