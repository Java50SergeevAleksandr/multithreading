package telran.multithreading;

import telran.multithreading.consumer.Receiver;
import telran.multithreading.messaging.*;
import telran.multithreading.producer.Sender;

public class SenderReceiverAppl {

	private static final int N_MESSAGES = 20;
	private static final int N_RECEIVERS = 10;

	public static void main(String[] args) throws InterruptedException {
		MessageBox messageBox = new MessageBoxString();
		Receiver[] reseivers = new Receiver[N_RECEIVERS];
		Sender sender = new Sender(messageBox, N_MESSAGES);
		sender.start();
		for (int i = 0; i < N_RECEIVERS; i++) {
			reseivers[i] = new Receiver(messageBox);
			reseivers[i].start();
		}
		sender.join();
		for (Receiver r : reseivers) {
			r.interrupt();
		}
	}

}
