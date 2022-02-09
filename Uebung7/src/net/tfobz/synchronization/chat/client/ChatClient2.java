package net.tfobz.synchronization.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.Socket;

/**
 * @author Michael
 * @see http://www.vorlesungen.uos.de/informatik/b06/
 */
public class ChatClient2 {
	public static final int PORT = 65535;
	
	public static void main(String[] args) {
		Socket client = null;
		try {
			client = new Socket(args[1], PORT);
			BufferedReader in = new BufferedReader( new InputStreamReader(client.getInputStream()));
			PrintStream out = new PrintStream(client.getOutputStream());
			
			// sending the name of the client to the server
			out.println(args[0]);
			
			new ChatClientThread(in).start();
			
			for (int i = 0; i < 50; i++) {
				// Synchronisiert
				sendMessage(args[0], out);
			}
			
		} catch (IOException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try { client.close(); } catch (Exception e1) { ; }
		}
	}
	
	public static synchronized void sendMessage(String name, PrintStream out) throws InterruptedException {
		out.println("I am " + name);
		Thread.sleep(1000);
	}
	
}
