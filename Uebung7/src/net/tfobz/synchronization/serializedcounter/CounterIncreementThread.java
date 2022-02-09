package net.tfobz.synchronization.serializedcounter;

public class CounterIncreementThread extends Thread {
	SerializedCounter counter;
	// Index wird benutzt um zu kontrollieren welcher Thread gerade inkrementiert
	int index = 0;
	
	public CounterIncreementThread(int index, SerializedCounter c) {
		this.counter = c;
		this.index = index;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 1000; i++)
			System.out.println("Thread "+ index + ": " + counter.getIncrementedValue());
	}

}
