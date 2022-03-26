package net.tfobz.tunnel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * In dieser Konsolenanwendung wird zuerst ein VisitorsMonitor angelegt, und dann 
 * wartet das Programm in einer Endlosschleife auf Clientanfragen. Erreicht ihm 
 * eine solche, so wird diese in einem Thread vom Typ ServerThread abgearbeitet.
 * Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
 * können mehrere Anfragen gleichzeitig bearbeitet werden
 */
public class ServerMain {
	/**
	 * Port an welchem der Server arbeitet
	 */
	protected static final int PORT = 65535;
	protected static ServerSocket server = null;
	protected static Socket client = null;
	
	/**
	 * Besuchermonitor wird angelegt, und in einer Endlosschleife wird auf 
	 * Clientanfragen gewartet, welche alle über einzelne ServerThreads abgearbeitet
	 * werden. Dadurch dass jede Anfrage in einem eigenen Thread abgearbeitet wird,
	 * können mehrere Anfragen gleichzeitig bearbeitet werden
	 * @param args
	 */
	public static void main(String[] args) {
		VisitorsMonitor vm = new VisitorsMonitor();
		try {
			server = new ServerSocket(PORT);
			while (true) {
				client = server.accept();
				ServerThread st = new ServerThread(client, vm);
				st.start();
				st.join();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode zur Exceptionbehandlung
	 * @param e
	 */
	public static void behandleException(Exception e) {
		e.printStackTrace();
	}
}
