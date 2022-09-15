package net.tfobz.synchronization.serializedcounter;

public class SerializedCounterTest {

	public static void main(String[] args) {
		SerializedCounter counter = new SerializedCounter();
		counter.resetCounter();
		CounterIncreementThread ct1 = new CounterIncreementThread(0, counter);
		CounterIncreementThread ct2 = new CounterIncreementThread(1, counter);
		
		Thread t = new Thread() {
			public void run() {
				ct1.start();
				ct2.start();
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
