package net.tfobz.imagecompress;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Benjamin Demetz
 *
 */
public class Hauptprogramm extends JFrame {
	// Komponente die das Bild beinhaltet
	private ImageComponent image;
	// Das Bild selbst
	private BufferedImage bimage;
	private JButton open;
	private JButton compress_save;
	private JButton compress;
	private JSpinner granulat;
	private JProgressBar progress;
	private JFileChooser filer;
	private String path;
	
	public static void main(String args[]) {
		Hauptprogramm h = new Hauptprogramm();
		h.setVisible(true);
	}
	
	public Hauptprogramm() {
		this.setTitle("JPG-Compression");
		this.setBounds(100, 100, 995, 640);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.filer = new JFileChooser();
		this.filer.setBounds(100, 100, 900, 700);
		
		this.image = new ImageComponent();
		this.image.setBounds(10, 10, 960, 540);
		this.image.setToolTipText("Am besten 1920x1080 Bilder!");
		
		this.open = new JButton("Open...");
		this.open.setBounds(10, 560, 80, 30);
		this.open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Macht einen neuen Dialog auf um eine Datei auszuwählen
				int ergebnis = filer.showOpenDialog(Hauptprogramm.this);
				if (ergebnis == JFileChooser.APPROVE_OPTION) {
					File im = filer.getSelectedFile();
					// Setzt das Bild auf
					if (im != null && !im.isDirectory()) {
						try {
							// Setzt das Bild im JFrame
							image.setImage(im);
							// Ordner in dem sich die ausgewählte Datei befindet
							path = im.getParent();
							// Liest Daten des Bildes ein und bildet somit eine BufferedImage
							bimage = ImageIO.read(im);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		// Benutzer kann die Granulität wählen
		this.granulat = new JSpinner();
		this.granulat.setBounds(100, 560, 70, 30);
		SpinnerNumberModel model = new SpinnerNumberModel(0.01, 0.01, 0.1, 0.01);
		this.granulat.setModel(model);
		
		this.progress = new JProgressBar();
		this.progress.setStringPainted(true);
		this.progress.setBounds(480, 560, 490, 30);
		
		// Komprimieren und abspeichern der Bilder
		this.compress_save = new JButton("Compress and save");
		this.compress_save.setBounds(180, 560, 180, 30);
		this.compress_save.addActionListener(new ActionListener() {
			// Externe Variabeln
			int qualitat = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ein Thread um alle Operationen auszuführen ohne den JFrame zu stoppen
				new Thread() {
					@Override
					public void run() {
						// Initiale Werte wiederhergestellt
						qualitat = 0;
						// Eine Liste mit alle Threads
						ArrayList<Thread> tList = new ArrayList<>();
						Thread t;
						// Granulitätsformel: 1/granulität + 1
						// Berechnet die Anzahl an benötigte Threads
						int anzahl = (int) (1 / Double.parseDouble(granulat.getValue().toString()) + 1);
						// Legt die Werte für die Progressbar fest
						progress.setMaximum(anzahl);
						progress.setValue(0);
						progress.setMinimum(0);

						// Thread dass die Progressbar kontrolliert
						Thread y = new Thread() {
							@Override
							public void run() {
								// Deaktiviert sämtliche Knöpfe und Input
								open.setEnabled(false);
								compress_save.setEnabled(false);
								compress.setEnabled(false);
								granulat.setEnabled(false);
								// Zählt die beendeten Threads
								int count = 0;
								int sss = tList.size();
								// Solange noch aktive tHreads sind
								while (count < sss) {
									for (int j = 0; j < tList.size(); j++) {
										// Kontrolliert ob ein Thread beendet wurde
										if (tList.get(j) != null && tList.get(j).getState() == Thread.State.TERMINATED) {
											// Wenn ja wird der counter erhöht und der Thread von der Liste entfernt
											count++;
											tList.remove(j);
										}
									}
									// Anschließend wird noch die Progressbar aktualisisert
									progress.setValue(count);
								}
							}
						};
						// Schleife um Threads zu erstellen und zu starten
						for (int i = 0; i < anzahl; i++) {
							// Erstellt neue Threads
							t = new Thread() {
								@Override
								public void run() {
									// Name der Datei wird gleich am Anfang festgelegt da später sich der Wert der Qualität ändert
									String name = "Quali_" + qualitat +".jpg";
									// Wird später die komprimierte Datei beinhalten
									BufferedImage bimag = null;
									try {
										if (qualitat != 0)
											bimag = JPGImageCompress.compressImage(bimage, qualitat/(anzahl-1));
										else
											bimag = JPGImageCompress.compressImage(bimage, qualitat);
										
										// Erstellt eine neue File Referenz
										File img_file = new File(path + "\\"+name);
										// Schreibt die Daten in die Datei
										ImageIO.write(bimag, "jpg", img_file);
										// Erstellt einen neuen File
										boolean hah = img_file.createNewFile();
										// DEBUG ONLY -->
										System.out.println("Created: "+hah + ", Name: '" + name + "' Exists: " + img_file.exists());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							};
							// Fügt den Thread der ArrayListe hinzu und startet es
							tList.add(t);
							t.start();
							qualitat++;
						}
						// Wartet ab bis der Thread zum updaten der Progressbar fertig ist
						try {
							y.start();
							y.join(0);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						// Schaltet alle Knöpfe wieder frei
						System.out.println("DONE");
						open.setEnabled(true);
						compress_save.setEnabled(true);
						compress.setEnabled(true);
						granulat.setEnabled(true);
					}
				}.start();
			}
		});
		
		this.compress = new JButton("Compress");
		this.compress.setBounds(370, 560, 100, 30);
		this.compress.addActionListener(new ActionListener() {
			int qualitat = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				new Thread() {
					@Override
					public void run() {
						// Initiale Werte wiederhergestellt
						qualitat = 0;
						// Eine Liste mit alle Threads
						ArrayList<Thread> tList = new ArrayList<>();
						Thread t;
						// Granulitätsformel: 1/granulität + 1
						// Berechnet die Anzahl an benötigte Threads
						int anzahl = (int) (1 / Double.parseDouble(granulat.getValue().toString()) + 1);
						// Legt die Werte für die Progressbar fest
						progress.setMaximum(anzahl);
						progress.setValue(0);
						progress.setMinimum(0);
						
						// Startet nur wenige Threads auf einmal um Speicher zu sparen
						Thread hauptthread = new Thread() {
							@Override
							public void run() {
								// Deaktiviert sämtliche Knöpfe und Input
								open.setEnabled(false);
								compress_save.setEnabled(false);
								compress.setEnabled(false);
								granulat.setEnabled(false);
								// Geht alle Threads in der Liste durch und kontrolliert die Progressbar
								for (int i = 0; i < tList.size(); i++) {
									try {
										tList.get(i).start();
										tList.get(i).join();
										progress.setValue(progress.getValue()+1);
										// DEBUG ONLY -->
										System.out.println("Bild: " + progress.getValue() + " von " + progress.getMaximum());
										// Wartet 5 Sekunden
										Thread.sleep(5000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						};
						// Schleife um alle Threads zu erstellen die dann die Bilder anzeigen
						for (int i = 0; i < anzahl; i++) {
							t = new Thread() {
								@Override
								public void run() {
									// Wird später die komprimierte Datei beinhalten
									BufferedImage bimag = null;
									try {
										if (qualitat != 0)
											bimag = JPGImageCompress.compressImage(bimage, qualitat/(anzahl-1));
										else
											bimag = JPGImageCompress.compressImage(bimage, qualitat);
										
										// Erstellt eine neue File Referenz --> Temporär
										File img_file = new File("temp_file");
										// Schreibt die Daten in die Datei
										ImageIO.write(bimag, "jpg", img_file);
										// Setz das Bild auf
										image.setImage(bimag);
										// Löscht die Datei wenn das Programm beendet wird
										img_file.deleteOnExit();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							};
							// Fügt den Thread der Liste hinzu um es später auszuführen
							tList.add(t);
						}
						try {
							// Startet das Hauptthread und wartet bis es fertig ausgeführt hat
							hauptthread.start();
							hauptthread.join();
							System.out.println("Done");
							// Aktiviert wieder sämtliche Input-Felder
							open.setEnabled(true);
							compress_save.setEnabled(true);
							compress.setEnabled(true);
							granulat.setEnabled(true);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}.start();
			}
		});
		// Fügt alle Komponente den JFrame hinzu
		Container c = this.getContentPane();
		c.setLayout(null);
		c.add(this.image);
		c.add(this.open);
		c.add(this.granulat);
		c.add(this.compress_save);
		c.add(this.compress);
		c.add(this.progress);
	}
}
