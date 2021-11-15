package net.tfobz.threadanalyse;

public class MyThreadStateAnalyser extends Thread {
	Thread thread;
	
	public MyThreadStateAnalyser(Thread t) {
		this.thread = t;
	}
	
	@Override
	public void run() {
		do {
			System.out.println(thread.getState());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!thread.isInterrupted() && thread.getState() != Thread.State.TERMINATED);
		System.out.println(thread.getState());
	}
}
