package telran.multithreading.consumer;

import telran.multithreading.messaging.MessageBox;

public class Receiver extends Thread {
	private MessageBox messageBox;

	public Receiver(MessageBox messageBox) {
		this.messageBox = messageBox;

	}

	@Override
	public void run() {
		String message = null;
		try {
			while (true) {
				message = messageBox.take();
				print(message);
			}
		} catch (InterruptedException e) {
			while ((message = messageBox.pull()) != null) {
				print(message);
			}

			System.out.println("end Receiver " + getId());
		}

	}

	private void print(String message) {
		System.out.printf("thread id: %d, message: %s\n", getId(), message);
	}
}
