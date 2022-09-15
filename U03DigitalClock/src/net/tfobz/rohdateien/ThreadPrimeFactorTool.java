package net.tfobz.rohdateien;

public class ThreadPrimeFactorTool extends PrimeFactorTool {
	
	@Override
	public void printPrimeFactors(int num) {
		// Erstellt einen Thread für jeden Aufruf
		new Thread() {
			public void run() {
				ThreadPrimeFactorTool.super.printPrimeFactors(num);
			}
		}.start();
	}
}
