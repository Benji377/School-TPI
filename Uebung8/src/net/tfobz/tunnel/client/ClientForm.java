package net.tfobz.tunnel.client;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 * Diese Klasse erstellt die Benutzerschnittstelle und den GuidesMonitor zur 
 * Verwaltung der Gruppenführer pro Eingang. Sie enthält auch die 
 * Ereignisbehandlungsmethoden für die beiden Knöpfe. In diesen Methoden werden 
 * die Objekte vom Typ ClientThread zur Behandlung der Clientanfragen angelegt 
 * und die Threads gestartet.<br><br>
 * <b>Ereignisbehandlungsmethode Besichtigung anfordern</b><br>
 * Diese Methode kontrolliert zuerst, ob eine Besucherzahl ins Textfeld
 * eingegeben wurde und konvertiert den Inhalt in eine Zahl. Diese Zahl
 * darf nicht größer sein als das maximale Fassungsvermögen des Tunnels.
 * Dann wird der ClientThread gestartet, dem diese Besucheranzahl und
 * die Referenzen auf das ClientForm sowie auf den GuidesMonitor
 * übergeben werden.<br><br>
 * <b>Ereignisbehandlungsmethode Besichtigung beenden</b><br>
 * Zuerst wird kontrolliert ob es überhaupt Aktive Besichtigungen gibt,
 * welche von diesem Eingang aus den Tunnel betreten haben. Sind solche
 * vorhanden, dann wird kontrolliert, ob eine aktive Besichtigung 
 * ausgewählt wurde. Ist dies der Fall so wird aus dem Text des ausgewählten
 * JList-Eintrages die Anzahl der Besucher ermittelt und in eine Zahl
 * konvertiert. Dann wird der ClientThred gestartet, dem diese negative (!)
 * Anzahl und Referenzen auf ClientForm und GuidesMonitor 
 * übergeben werden
 */
public class ClientForm extends JFrame {

	/**
	 * Monitor durch welchen am Eingang ein Führer reserviert werden kann
	 */
	private GuidesMonitor guidesMonitor = null;
	/**
	 * Modell zur Verwaltung der Inhalte der JList
	 */
	protected DefaultListModel<String> mActiveVisits = null;

	/**
	 * Legt das Formular an und macht es sichtbar. Beim Anlegen des Forumulas
	 * wird auch der GuidesMonitor angelegt. Nachdem das Formular angelegt wurde,
	 * werden in Abständen von einer Sekunde Serveranfragen geschickt zur 
	 * Ermittlung der verfügbaren Besucher, d. h. der Server antwortet und
	 * liefert die Anzahl je Besucheranzahl zurück die noch in den Tunnel 
	 * eingelassen werden kann
	 * @param args
	 */
	public static void main(String[] args) {
	}
}