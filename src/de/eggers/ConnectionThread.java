package de.eggers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */
public class ConnectionThread extends Thread {
    private Socket socket;
    private boolean readyState;
 
    public ConnectionThread(Socket socket) {
        this.socket = socket;
    }
    
    /**/
 
    public boolean isReady() {
    	return this.readyState;
    }
    
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            String text;
 
            do {
                text = reader.readLine();
                if(text == "RDY") {
                	this.readyState = true;
                }
                //String reverseText = new StringBuilder(text).reverse().toString();
                //writer.println("Server: " + reverseText);
            } while (!text.equals("bye"));
 
            reader.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}