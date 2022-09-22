package net.tfobz.state.ampel.rohdateien;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.JFrame;

/**
 * Diese Klasse stellt ein Fenster bereit dessen Hintergrund eine
 * Kreuzung darstellt. Das Fenster ist so groß wie das Bild und kann
 * in seiner Größe nicht geändert werden
 * @author Michael Wild
 *
 */
public class KreuzungFrame extends JFrame
{
	private Image kreuzungBild = null;
	
	public KreuzungFrame() {
		setResizable(false);
		setLayout(null);
		URL url = this.getClass().getResource("kreuzung.png");
		if (url == null)
			System.out.println("kreuzung.png nicht gefunden");
		else {
			this.kreuzungBild = getToolkit().getImage(url);
			prepareImage(kreuzungBild, this);
	    // Warte bis die Eigenschaften des Bildes geladen sind
	    while ((checkImage(kreuzungBild, this) & PROPERTIES) != PROPERTIES) {
	      try {
	        // Pause, um dem Ladevorgang keine Ressourcen zu nehmen
	        Thread.sleep(50);
	      } catch(InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	    // Stelle Größe des Objektes auf Größe des Bildes ein
	    this.setSize(this.kreuzungBild.getWidth(this),this.kreuzungBild.getHeight(this));
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (this.kreuzungBild != null)
			g.drawImage(this.kreuzungBild, 0, 0, this.kreuzungBild.getWidth(this), this.kreuzungBild.getHeight(this), null);
	}
}
