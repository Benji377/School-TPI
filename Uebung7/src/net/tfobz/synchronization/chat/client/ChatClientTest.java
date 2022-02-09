package net.tfobz.synchronization.chat.client;

public class ChatClientTest {
	
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++) {
			new Thread() {
				public void run() {
					String[] arguments = new String[] {this.getName(), "127.0.0.1"};
					ChatClient2.main(arguments);
				}
			}.start();
		}
	}
}
