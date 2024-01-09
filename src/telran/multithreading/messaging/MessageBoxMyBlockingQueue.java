package telran.multithreading.messaging;

/**
 * Message box contains only one string
 */
public class MessageBoxMyBlockingQueue implements MessageBox {
	private MyBlockingQueue<String> messages = new MyLinkedBlockingQueue<>(1);

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
