package net.tfobz.tunnel.client;

/**
 * An ihm kann ein Führer angefordert aber auch ein solcher zurückgegeben 
 * werden. Dieser muss eine Referenz auf ClientForm haben, damit die 
 * Statusmeldungen dort angezeigt werden können
 */
public class GuidesMonitor 
{
	/**
	 * Maximalanzahl der am Eingang vorhanden Führer
	 */
	protected final int MAX_GUIDES = 4;
	/**
	 * Anzahl der momentan verfügbaren Führer
	 */
	protected int availableGuides = MAX_GUIDES;
	/**
	 * Referenz auf das ClientForm um Statustexte auszugeben
	 */
	protected ClientForm clientForm = null;
	
	/**
	 * Konstruktor, dem eine Referenz auf das ClientForm übergeben wird
	 * @param clientForm
	 */
	public GuidesMonitor(ClientForm clientForm) {
		this.clientForm = clientForm;
	}
	
	/**
	 * Ein Führer wird angefordert, gleichzeitig werden die Statusmeldungen im
	 * ClientForm ausgegeben und die Benutzerschnittstelle angepasst
	 */
	public synchronized void request() {
		System.out.println("Guide request, av.: " + getAvailableGuides());
		clientForm.status_area.setText(clientForm.status_area.getText() + 
				"Guide requested\n");
		if (availableGuides > 0) {
			this.availableGuides -= 1;
			clientForm.avail_guid.setText("Available guides: " + getAvailableGuides());
			clientForm.status_area.setText(clientForm.status_area.getText() + 
					"Guide added, " + getAvailableGuides() + " remaining\n");
		} else {
			clientForm.status_area.setText(clientForm.status.getText() +
					"No more guides available\n");
		}
		
	}
	
	/**
	 * Führer wird bei Beendigung einer Führung zurück gegeben. Statusmeldungen 
	 * werden ausgegeben und die Benutzerschnittstelle angepasst
	 */
	public synchronized void release() {
		System.out.println("Guide release, av.: " + getAvailableGuides());
		this.availableGuides += 1;
		clientForm.avail_guid.setText("Available guides: " + getAvailableGuides());
		clientForm.status_area.setText(clientForm.status_area.getText() + 
				"Guide released, " + getAvailableGuides() + " remaining\n");
	}

	/**
	 * Die Anzahl der momentan verfügbaren Führer wird zurück geliefert
	 * @return Anzahl der momentan verfügbaren Führer
	 */
	public synchronized int getAvailableGuides() {
		return this.availableGuides;
	}
}
