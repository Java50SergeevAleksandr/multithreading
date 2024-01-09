package telran.multithreading.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Message box contains only one string
 */
public class MessageBoxBlockingQueue implements MessageBox {
	private BlockingQueue<String> messages = new LinkedBlockingQueue<>();

	@Override
	public void put(String message) throws InterruptedException {
		messages.put(message);
	}

	@Override
	public String take() throws InterruptedException {
		return messages.take();
	}

	@Override
	public String poll() {
		return messages.poll();
	}

}
