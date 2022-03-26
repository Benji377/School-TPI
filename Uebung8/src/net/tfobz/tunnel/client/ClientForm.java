package net.tfobz.tunnel.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 * Diese Klasse erstellt die Benutzerschnittstelle und den GuidesMonitor zur 
 * Verwaltung der Gruppenführer pro Eingang. Sie enthält auch die 
 * Ereignisbehandlungsmethoden für die beiden Knöpfe. In diesen Methoden werden 
 * die Objekte vom Typ ClientThread zur Behandlung der Clientanfragen angelegt 
 * und die Threads gestartet.<br>
 * <br>
 * <b>Ereignisbehandlungsmethode Besichtigung anfordern</b>
 * <br>
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
	protected int max_tunnelers = 0;
	
	protected JLabel entrance_label;
	protected JPanel guides_panel;
	protected JPanel visits_panel;
	protected JLabel avail_guid;
	protected JLabel visitors;
	protected JTextField visitors_input;
	protected JButton request_visit;
	protected JLabel active_visits;
	protected JList<String> visitor_list;
	protected JButton finish_visit;
	protected JLabel avai_visitors;
	protected JLabel status;
	protected JTextArea status_area;
	protected JScrollPane status_area_pane;

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
		ClientForm cf = new ClientForm();
		cf.setVisible(true);
	}
	
	public ClientForm() {
		guidesMonitor = new GuidesMonitor(this);
		mActiveVisits = new DefaultListModel<>();
		
		this.setTitle("Entrance 1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(300, 300, 500, 600);
		this.setResizable(false);
		
		entrance_label = new JLabel("Entrance 1");
		entrance_label.setBounds(5, 5, 200, 30);
		entrance_label.setFont(new Font("", Font.BOLD, 20));
		
		guides_panel = new JPanel();
		guides_panel.setBounds(5, 40, 230, 150);
		guides_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		guides_panel.setLayout(null);
		
		visits_panel = new JPanel();
		visits_panel.setBounds(5, 200, 230, 300);
		visits_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		visits_panel.setLayout(null);
		
		avail_guid = new JLabel("Available guides: " + guidesMonitor.getAvailableGuides());
		avail_guid.setBounds(5, 15, 200, 30);
		avail_guid.setFont(new Font("", Font.BOLD, 16));
		guides_panel.add(avail_guid);
		
		visitors = new JLabel("Visitors:");
		visitors.setBounds(5, 60, 100, 30);
		visitors.setFont(new Font("", Font.BOLD, 16));
		guides_panel.add(visitors);
		
		visitors_input = new JTextField();
		visitors_input.setBounds(170, 60, 50, 30);
		guides_panel.add(visitors_input);
		
		request_visit = new JButton("Request visit");
		request_visit.setBounds(15, 110, 200, 30);
		this.getRootPane().setDefaultButton(request_visit);
		request_visit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int anzahl = Integer.parseInt(visitors_input.getText());
					// Durch diesen Aufruf wird die maximale Anzahl an Besucher im Tunnel ermittelt
					ClientThread ct = new ClientThread(0, ClientForm.this, guidesMonitor);
					ct.start();
					ct.join();
					// Kontrolliert ob die Anzahl gebucht werden soll
					if (anzahl <= max_tunnelers && anzahl != 0) {
						// Bucht die Anzahl an Besucher ab
						ClientThread ct2 = new ClientThread(anzahl, ClientForm.this, guidesMonitor);
						ct2.start();
						ct2.join();
						// Löscht den eingegebenen Wert, wenn die Aktion erfolgreich war
						visitors_input.setText("");
					}
					
				} catch (NumberFormatException f) {
					f.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		guides_panel.add(request_visit);
		
		active_visits = new JLabel("Active visits:");
		active_visits.setBounds(5, 5, 200, 30);
		active_visits.setFont(new Font("", Font.BOLD, 16));
		visits_panel.add(active_visits);
		
		visitor_list = new JList<>();
		visitor_list.setBounds(15, 40, 200, 210);
		visitor_list.setFont(new Font("", Font.BOLD, 16));
		visitor_list.setModel(mActiveVisits);
		visits_panel.add(visitor_list);
		
		finish_visit = new JButton("Finish visit");
		finish_visit.setBounds(15, 260, 200, 30);
		finish_visit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Ermittelt den selected Index
					int ind = visitor_list.getSelectedIndex();
					if (ind < 0) {
						ind = 0;
					} else {
						ind = ind * (-1);
					}
					// Index muss um eins zurück gesetzt werden , denn 0 darf nicht gesendet werden
					ind -= 1;
					// Erstellt ein Thread um den String aus der Liste zu entfernen
					ClientThread ct = new ClientThread(ind, ClientForm.this, guidesMonitor);
					ct.start();
					ct.join();
					// Hebt die Auswahl wieder auf
					visitor_list.clearSelection();
				} catch (InterruptedException ff) {
					ff.printStackTrace();
				}
			}
		});
		visits_panel.add(finish_visit);
		
		avai_visitors = new JLabel("Available Visitors: 50");
		avai_visitors.setBounds(15, 500, 200, 30);
		avai_visitors.setFont(new Font("", Font.BOLD, 16));
		
		status = new JLabel("Status:");
		status.setBounds(250, 15, 230, 30);
		status.setFont(new Font("", Font.BOLD, 16));
		
		status_area = new JTextArea();
		status_area.setBorder(BorderFactory.createLineBorder(Color.black));
		status_area.setFont(new Font("", Font.PLAIN, 16));
		status_area.setEditable(false);
		
		status_area_pane = new JScrollPane(status_area);
		status_area_pane.setBounds(250, 40, 230, 510);
		
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(avai_visitors);
		c.add(entrance_label);
		c.add(guides_panel);
		c.add(visits_panel);
		c.add(status);
		c.add(status_area_pane);
	}
}