package net.tfobz.threadanalyse;

public class Hauptprogramm {
	
	public static void main(String args[]) {
		Thread thread = new MyThread();
		Thread analyser = new MyThreadStateAnalyser(thread);
		Thread terminator = new MyThreadTerminator(thread);
		
		analyser.start();
		terminator.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread.start();
	}
}
