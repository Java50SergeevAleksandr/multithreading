package telran.multithreading.messaging;

import java.util.concurrent.locks.*;

/**
 * Message box contains only one string
 */
public class MessageBoxCondition implements MessageBox {
	private String message;
	Lock lock = new ReentrantLock();
	Condition producerWaiting = lock.newCondition();
	Condition consumerWaiting = lock.newCondition();

	@Override
	public void put(String message) throws InterruptedException {
		try {
			lock.lock();
			while (this.message != null) {
				producerWaiting.await();
			}
			this.message = message;
			consumerWaiting.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String take() throws InterruptedException {
		try {
			lock.lock();
			while (message == null) {
				consumerWaiting.await();
			}
			String res = message;
			message = null;
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String poll() {
		try {
			lock.lock();
			String str = message;
			message = null;
			producerWaiting.signal();
			return str;
		} finally {
			lock.unlock();
		}
	}

}
