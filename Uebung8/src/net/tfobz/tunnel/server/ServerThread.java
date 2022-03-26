package net.tfobz.tunnel.server;

import java.io.*;
import java.net.Socket;

/**
 * Der Thread liest vom Socket die Anzahl, und dabei werden die drei Fälle 
 * – größer 0, kleiner 0 oder gleich 0 – unterschieden und entsprechen am 
 * VisitorsMonitor die Anfragen gestellt. Das  Ergebnis wird an den Client 
 * zurück geschickt. der ServerThread erhält den Socket des Clients und eine 
 * Referenz auf VisitorsMonitor
 */
public class ServerThread extends Thread
{
	/**
	 * Der Clientsocket von welchem die Besucheranzahl gelesen werden kann
	 */
	protected Socket client = null;
	/**
	 * VisitorsMonitor an dem die Anfrage nach Besuchern bzw. die Rückgabe
	 * der Besucher nach Beendigung einer Besichtigung gestellt werden kann
	 */
	protected VisitorsMonitor visitorsMonitor = null;
	
	/**
	 * Konstruktor erhält den Clientsocket und den VisitorsMonitor als
	 * Referenz. Als Threadname wird die IP-Adresse des Clients gesetzt.
	 * Die IP-Adresse kann über den Clientsocket durch die Methode
	 * getInetAdress() erfragt werden
	 * @param client
	 * @param visitorsMonitor
	 */
	public ServerThread(Socket client, VisitorsMonitor visitorsMonitor) {
		this.setName("" + client.getInetAddress());
		this.client = client;
		this.visitorsMonitor = visitorsMonitor;
	}
	
	/**
	 * Diese Methode liest zuerst vom Clientsocket die Anzahl. Je nach dem
	 * welche Werte in anzahl stehen, werden folgende Aufgaben erledigt:<br><br>
	 * <b>anzahl == 0</b><br>
	 * Es wird die Anzahl der am VisitorsMonitor momentan verfügbaren Benutzer
	 * abgefragt und an den Client zurück geschickt<br><br>
	 * <b>anzahl > 0</b><br>
	 * Es werden am VisitorsMonitor die Benutzer angefordert<br><br>
	 * <b>anzahl < 0</b><br>
	 * Es werden dem VisitorsMonitor die Anzahl an Benutzer zurück gegeben
	 */
	public void run() {
		try {
			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();
			int anzahl = (byte)in.read();
			if (anzahl == 0) {
				// Besucheranzahl zurückgeben
				int anz = visitorsMonitor.getAvailableVisitors();
				out.write(anz);
			} else if (anzahl > 0) {
				// Neue Besichtigung
				int anz = visitorsMonitor.getAvailableVisitors();
				visitorsMonitor.request(anzahl);
				if (anz > visitorsMonitor.getAvailableVisitors()) {
					// Besucher wurden gebucht
					out.write(visitorsMonitor.getAvailableVisitors());
				} else {
					// Keine Besucher gebucht
					out.write(-1);
				}
			} else if (anzahl < 0) {
				// Besichtigung soll beendet werden
				anzahl *= -1;
				visitorsMonitor.release(anzahl);
				out.write(visitorsMonitor.getAvailableVisitors());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
