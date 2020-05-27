package de.eggers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
    	ServerThread srv = new ServerThread(50500);
        System.out.println("\r\nRunning Server: " + 
                "Host=" + srv.getSocketAddress().getHostAddress() + 
                " Port=" + srv.getPort());
    	srv.start();
    	
    	/*
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    	
    	while(true) {
    		List clients = srv.getClients();
    		int a = 0;
    		for(Iterator<ConnectionThread> i = clients.iterator(); i.hasNext();) {
    			ConnectionThread ct = i.next();
    			if(ct.isReady()) {
    				a++;
    			}
    		}
    		if(a == clients.size()) {
    			System.out.println("Let's go");
    			break;
    		}
    	}
    	
    	srv.stopRun = true;
    	try {
			srv.join();
		} catch (InterruptedException e) {
			System.out.println("Join wurde abgebrochen: " + e.getMessage());
		}
    	
    	System.out.println("Ende");
    }
}
