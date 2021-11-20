package net.tfobz.imagecompress;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Hauptprogramm extends JFrame {
	private ImageComponent image;
	private JButton open;
	private JButton compress_save;
	private JButton compress;
	private JSpinner granulat;
	private JProgressBar progress;
	private JFileChooser filer;
	
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
							image.setImage(im);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		this.granulat = new JSpinner();
		this.granulat.setBounds(100, 560, 70, 30);
		SpinnerNumberModel model = new SpinnerNumberModel(0.01, 0.01, 0.1, 0.01);
		this.granulat.setModel(model);
		
		this.compress_save = new JButton("Compress and save");
		this.compress_save.setBounds(180, 560, 180, 30);
		this.compress_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Granulitätsformel: 1/granulität + 1
				int anzahl = (int) (1 / Double.parseDouble(granulat.getValue().toString()) + 1);
				for (int i = 0; i < anzahl; i++) {
					
				}
				
			}
		});
		
		this.compress = new JButton("Compress");
		this.compress.setBounds(370, 560, 100, 30);
		
		this.progress = new JProgressBar();
		this.progress.setStringPainted(true);
		this.progress.setBounds(480, 560, 490, 30);
		
		
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
