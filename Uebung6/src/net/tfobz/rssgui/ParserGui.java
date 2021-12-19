package net.tfobz.rssgui;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import net.tfobz.rssreader.AdvancedParser;
import net.tfobz.rssreader.Channel;

public class ParserGui extends JFrame{
	private JButton addUrl;
	private JButton scheduler;
	private JButton update;
	private JScrollPane spane;
	private JTextArea texta;
	// Executoren
	private ExecutorService ex;
	private ScheduledExecutorService schedex;
	private List<Channel> channelist = new ArrayList<>();
	private List<Callable<String>> tasks = new ArrayList<>();
	private AdvancedParser ap;
	private Image tImage;
	private TrayIcon ticon = null;
	// Kontrolliert die anzahl an Items aller Channels, kann somit ermitteln ob es neue Items gibt
	private int itemSize = 0;
	
	public static void main(String[] args) {
		ParserGui pg = new ParserGui();
		pg.setVisible(true);
	}
	
	
	public ParserGui() {
		this.setTitle("Simple RSSReader");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(20, 20, 700, 400);
		this.setResizable(false);
		
		// Es wird hier versucht einen TrayIcon hinzuzufügen
		try {
			//Zuerst wird das Bild eingelesen
			tImage = ImageIO.read(getClass().getResource("rss_icon.png"));
			// Kontrolliert dass man auf diesem System TrayIcons benutzen kann
			if (SystemTray.isSupported()) {
				// Fügt dem System das Icon hinzu
				SystemTray stray = SystemTray.getSystemTray();
				ticon = new TrayIcon(tImage);
				ticon.setImageAutoSize(true);
				ticon.setToolTip("Simple RSSReader");
				stray.add(ticon);
			}
		} catch (IOException | AWTException n) {
			n.printStackTrace();
		}
		// Textarea in dem alles ausgegeben wird
		texta = new JTextArea();
		texta.setEditable(false);
		
		// Scrollpane enthält die Textarea und ermöglicht das scrollen wenn der Text zu lang wird
		spane = new JScrollPane(texta);
		spane.setBounds(5, 5, 675, 315);
		
		// Knopf um RSS-Feeds dem Programm hinzuzufügen
		addUrl = new JButton("Add URL");
		addUrl.setBounds(340, 325, 90, 30);
		addUrl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Es wird ein simpler Inputdialog benutzt
				String url = JOptionPane.showInputDialog("Enter new URL");
				if (url != null && !url.isEmpty()) {
					// Es wird ein neuer Parser aufegabaut
					ap = new AdvancedParser();
					try {
						ap.parser(url);
					} catch (IOException | XMLStreamException e1) {
						e1.printStackTrace();
					}
					// Der übergebene Channel wird der Liste hinzugefügt
					channelist.add(ap.getChannel());
					ex = Executors.newFixedThreadPool(channelist.size());
					// Für jedes Channel in der Liste wird ein neuer Callable gebaut
					// Dieser wird später für den Executor gebraucht
					for (int i = 0; i < channelist.size(); i++) {
						Channel c = channelist.get(i);
						itemSize += c.getItems().size();
						Callable<String> task = new Callable<String>() {

							@Override
							public String call() throws Exception {
								// Callable updated lediglich den Text im TextArea
								String newtext = texta.getText();
								newtext += "Channel: " + c.getTitle() + " ";
								newtext += "Newest item: " + c.getItems().get(c.getItems().size()-1).getTitle() + " ";
								newtext += "Date: " + c.getItems().get(c.getItems().size()-1).getPubDate() + "\n";
								texta.setText(newtext);
								return "DONE: " + newtext;
							}
						};
						// Tasks beinhaltet eine Liste dieser Callable
						tasks.add(task);
					}
				}
			}
		});
		// Scheduler aktivieren oder deaktivieren
		scheduler = new JButton("Activate Scheduler");		
		scheduler.setBounds(440, 325, 150, 30);
		scheduler.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scheduler.getText().equals("Deactivate Scheduler")) {
					scheduler.setText("Activate Scheduler");
					// Scheduler wird deaktiviert
					schedex.shutdown();
				} else {
					// Scheduler wird neu erstellt
					scheduler.setText("Deactivate Scheduler");
					schedex = Executors.newScheduledThreadPool(channelist.size());
					// Kontrolliert die Anzahl an Items
					int testi = 0;
					for (int i = 0; i < tasks.size(); i++) {
						// Startet den Scheduler und zählt die Items
						schedex.schedule(tasks.get(i), 10, TimeUnit.SECONDS);
						Channel c = channelist.get(i);
						testi += c.getItems().size();
					}
					texta.setText(texta.getText() + "Message: Scheduler now running (schedule period = 10 sec)\n");
					if (testi > itemSize) {
						// Wenn die neue Anzahl an Items größer ist, wird ein Hinweis darauf ausgegeben
						ticon.displayMessage("New Items", "There are new RSS Items", MessageType.INFO);
					}
				}
			}
		});
		// Knopf um den Feed manuell zu updaten
		update = new JButton("Update");
		update.setBounds(600, 325, 80, 30);
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Wenn der Executor nicht null ist, wird er gestartet
				if (ex != null) {
					try {
						texta.setText(texta.getText() + "Message: Updating Channels...\n");
						ex.invokeAll(tasks);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(addUrl);
		c.add(scheduler);
		c.add(update);
		c.add(spane);
	}
}
