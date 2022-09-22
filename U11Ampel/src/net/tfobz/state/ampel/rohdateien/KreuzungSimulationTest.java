package net.tfobz.state.ampel.rohdateien;

import javax.swing.JFrame;

public class KreuzungSimulationTest extends KreuzungFrame
{

	public static void main(String[] args) {
		KreuzungSimulationTest t = new KreuzungSimulationTest();
		t.setVisible(true);
	}

	public KreuzungSimulationTest() {
		setTitle("KreuzungSimulationTest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AmpelComponent a = new AmpelComponent();
		a.setBounds(415, 300, 0, 0);
		add(a);
	}
}
