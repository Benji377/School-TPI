package net.tfobz.rohdateien;

public class ThreadPrimeFactorTool extends PrimeFactorTool {
	
	@Override
	public void printPrimeFactors(int num) {
		// Erstellt einen Thread f�r jeden Aufruf
		new Thread() {
			public void run() {
				ThreadPrimeFactorTool.super.printPrimeFactors(num);
			}
		}.start();
	}
}
