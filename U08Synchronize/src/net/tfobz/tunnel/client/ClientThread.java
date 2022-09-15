package net.tfobz.tunnel.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.io.OutputStream;

/**
 * Jede Anfrage um Start einer Besichtigung oder Beendigung einer solchen muss in 
 * einem eigenen Thread durchgeführt werden, da insbesondere bei nicht 
 * Vorhandensein eines Führers oder bei nicht verfügbarem Besucherkontingent eine 
 * solche Anfrage längere Zeit warten und deshalb das ClientFormu blockiert 
 * würde.<br>
 * Damit der Thread seine Aufgabe durchführen kann, muss er einerseits den 
 * GuidesMonitor als Referenz enthalten, um die Führeranforderung zu stellen. 
 * Andererseits muss er ClientForm kennen, um die Ausgaben und Anpassungen an 
 * der Benutzerschnittstelle vornehmen zu können.<br>
 * Der Thread erhält die Besucheranzahl. Ist diese positiv, so fordert er zuerst 
 * beim GuidesMonitor einen Führer für die Gruppe an. Erhält er diese, so wird 
 * über eine Netzwerkverbindung mit dem Programm ServerMain Verbindung aufgenommen 
 * und um die eingegebene Anzahl von Besuchern angefragt.<br>
 * Ist die Besucheranzahl negativ, so bedeutet dies, dass die Führung beendet 
 * wird, der Führer dem GuidesMonitor zurück gegeben und beim Server ebenfalls 
 * die Besucheranzahl zurück gegeben wird.<br>
 * Ist die Besucheranzahl gleich 0, so wird der Thread anweisen beim Server die 
 * Anzahl der verfügbaren Besucher nachzufragen, die noch im Tunnel Platz haben
 */
public class ClientThread extends Thread
{
	/**
	 * IP-Adresse des Besucherservers
	 */
	protected static final String HOST = "127.0.0.1";
	/**
	 * Port
	 */
	protected static final int PORT = 65535;
	/**
	 * Falls positiv: Anzahl der anzufordernden Besucher die eine Besichtigung machen 
	 * möchten<br>
	 * Falls negativ: Anzahl der Besucher die eine Besichtigung beenden möchten<br>
	 * Falls 0: Besucherserver muss angewiesen werden, die Anzahl der Besucher
	 * zurückzuliefern, welche noch in den Tunnel eingelassen werden können
	 */
	protected int count = 0;
	/**
	 * Referenz auf das ClientForm. Diese ist notwendig, damit der ClientThread
	 * die Benutzerschnittstelle aktualisieren und z. B. Statusmeldungen dort
	 * anzeigen kann
	 */
	protected ClientForm clientForm = null;
	/**
	 * Referent auf den GuidesMonitor. Diese ist notwendig, dass der ClientThread
	 * an diesem einen Führer anfordern bzw. nach Beendigung einer Führung den
	 * Führer zurückgeben kann
	 */
	protected GuidesMonitor guidesMonitor = null;
	
	/**
	 * Konstruktor dem die Anzahl der Besucher, das ClientForm und der 
	 * GuidesMonitor übergeben wird
	 * @param anzahl
	 * @param clientForm
	 * @param guidesMonitor
	 */
	public ClientThread(int anzahl, ClientForm clientForm, 
		GuidesMonitor guidesMonitor) {
		this.count = anzahl;
		this.clientForm = clientForm;
		this.guidesMonitor = guidesMonitor;
	}
	
	/**
	 * In dieser Methode wird die eigentliche Arbeit des Threads erledigt. Ausgehend
	 * vom Wert der Variable count wird folgendes erledigt:<br><br>
	 * <b>count > 0: Eine neue Besichtigung soll durchgeführt werden</b><br>
	 * In einem ersten Schritt wird am GuidesMonitor ein Führer angefordert. War
	 * dies erfolgreich, so wird in einem zweiten Schritt eine Socket-Verbindung
	 * mit dem Server aufgebaut. Konnte die Verbindung nicht aufgebaut werden, so 
	 * wird der Führer wieder zurück gegeben. Bei aufrechter Verbindung wird die
	 * Anzahl übermittelt. Dann wartet der Thread auf die Antwort des Servers. Da
	 * der Thread neben anderen Threads eigenständig wartet, werden alle anderen
	 * Aktivitäten nicht blockiert. Nachdem die Antwort des Servers da ist, muss
	 * auch noch die JList mit einem neuen Eintrag ergänzt werden.Bei diesem Vorgang 
	 * müssen Änderungen und Ausgaben von Statusmeldungen am ClientFormular erfolgen<br><br>
	 * <b>count < 0: Eine Besichtigung soll beendet werden</b><br>
	 * Zuerst wird der Führer dem GuidesMonitor zurück gegeben. Dann wird eine
	 * Verbindung zum Server aufgebaut und diesem die Anzahl übergeben. Dann wird
	 * auf die Antwort des Servers gewartet und danach die JListeinträge aktualisiert. 
	 * Während dieses Vorganges werden die Inhalte von ClienForm angepasst<br><br>
	 * <b>count == 0: Eine Anfrage an den Server soll ermitteln, wie viele 
	 * Besucher noch im Tunnel Platz finden</b><br>
	 * Der Thread erstellt eine Socketverbindung mit dem Server, und schickt diesem
	 * eine 0. Der Server - falls aktiv - antwortet mit der aktuellen Besucheranzahl
	 * die noch in den Tunnel einglassen werden dürfen. Diese Anzahl wird im
	 * ClientForm ausgegeben
	 */
	public void run() {
		if (count > 0) {
			// Neue Besichtigung
			if (guidesMonitor.getAvailableGuides() > 0) {
				guidesMonitor.request();
				try {
					// Öffnet eine Verbindung zum Server
					Socket client = new Socket(HOST, PORT);
					OutputStream out = client.getOutputStream();
					// Schreibt die Anzahl an Besucher aus
					out.write(count);
					
					InputStream in = client.getInputStream();
					int response = (byte) in.read();
					
					// Bei fehlgeschlagener Aktion wird -1 zurückgegeben
					if (response == -1) {
						clientForm.status_area.setText(clientForm.status_area.getText() +
								"No visitors booked\n");
					} else {
						// Verbleibende Besucheranzahl wird aufgefrischt und Statusmeldung ausgegeben
						clientForm.avai_visitors.setText("Available Visitors: " + response);
						// String wird dem clientForm übergeben
						clientForm.mActiveVisits.addElement(count + " visitors");
						clientForm.status_area.setText(clientForm.status_area.getText() +
								count + " visitors booked" + "\n" + response + " visitors remaining\n");
					}
					
					client.close();
				} catch (IOException e) {
					guidesMonitor.release();
					clientForm.status_area.setText(clientForm.status_area.getText() + 
							"Connection error: Guide released\n");
					e.printStackTrace();
				}
			} else {
				// No Guide available
				clientForm.status_area.setText(clientForm.status_area.getText() + 
						"No Guide available\n");
			}
		} else if (count < 0) {
			// Besichtigung soll beendet werden
			if (guidesMonitor.getAvailableGuides() > 0) {
				guidesMonitor.release();
				try {
					Socket client = new Socket(HOST, PORT);
					OutputStream out = client.getOutputStream();
					// Count ist in diesem Fall der Index der Liste an der sich das Element befindet
					count *= -1;
					count -= 1;
					// Wir extrahieren die String und wandeln diese in Integer um mit Regex
					String list_item = clientForm.mActiveVisits.get(count);
					String extract = list_item.replaceAll("[^0-9]", "");
					int anz = Integer.parseInt(extract);
					anz *= -1;
					// Wir übergeben somit die Anzahl an Besucher die released werden sollen
					out.write(anz);

					InputStream in = client.getInputStream();
					int response = (byte) in.read();
					
					System.out.println("T: " + this.getName() + ": " + response);
					clientForm.avai_visitors.setText("Available Visitors: " + response);
					// Mit dem Index löschen wir sie aus dem clientForm
					String text = clientForm.mActiveVisits.remove(count);
					clientForm.status_area.setText(clientForm.status_area.getText() + text +
							" returned" + "\n" + response + " visitors remaining\n");
					
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// No Guide available
				clientForm.status_area.setText(clientForm.status_area.getText() + 
						"No Guide available\n");
			}
		} else if (count == 0) {
			// Anfrage an Server soll nachfragen wie viele noch im Tunnel platz haben
			try {
				Socket client = new Socket(HOST, PORT);
				OutputStream out = client.getOutputStream();
				out.write(count);
				
				InputStream in = client.getInputStream();
				int response = (byte) in.read();
				
				System.out.println("T: " + this.getName() + ": " + response);
				clientForm.max_tunnelers = response;
				clientForm.avai_visitors.setText("Available Visitors: " + response);
				clientForm.status_area.setText(clientForm.status_area.getText() + 
						response + " visitors available\n");
				
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Methode zur Behandlung der Netzwerkexceptions
	 * @param e
	 */
	public void behandleException(Exception e) {
		e.printStackTrace();
	}
}