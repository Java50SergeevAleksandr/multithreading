package telran.multithreading.messaging;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

import javax.swing.text.MutableAttributeSet;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
	private int capacity;
	private LinkedList<E> list = new LinkedList<>();
	private Lock mutex = new ReentrantLock();
	private Condition consumerWaiting = mutex.newCondition();
	private Condition producerWaiting = mutex.newCondition();

	public MyLinkedBlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	public MyLinkedBlockingQueue() {
		this(14);
	}

	@Override
	public boolean add(E obj) {
		try {
			mutex.lock();
			if (list.size() == capacity) {
				throw new IllegalStateException("Element cannot be added at this time due to capacity restrictions");
			}
			list.add(obj);
			consumerWaiting.signal();
			return true;
		} finally {
			mutex.unlock();
		}

	}

	@Override
	public boolean offer(E obj) {
		boolean res = true;
		try {
			mutex.lock();
			if (list.size() == capacity) {
				res = false;
			} else {
				res = list.add(obj);
				consumerWaiting.signal();
			}

			return res;

		} finally {
			mutex.unlock();
		}
	}

	@Override
	public void put(E obj) throws InterruptedException {
		try {
			mutex.lock();
			while (list.size() == capacity) {
				producerWaiting.await();
			}
			list.add(obj);
			consumerWaiting.signal();
		} finally {
			mutex.unlock();
		}

	}

	@Override
	public boolean offer(E obj, long timeout, TimeUnit unit) throws InterruptedException {
		Instant timeLimit = Instant.now().plus(timeout, unit.toChronoUnit());
		try {
			mutex.lock();
			while (list.size() == capacity && Date.from(Instant.now()).before(Date.from(timeLimit))) {
				producerWaiting.awaitUntil(Date.from(timeLimit));
				producerWaiting.await(timeout, unit);
			}
			if (list.size() == capacity) {
				return false;
			}
			list.add(obj);
			consumerWaiting.signal();
			return true;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E remove() {
		try {
			mutex.lock();
			E res = list.pop();
			producerWaiting.signal();
			return res;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E poll() {
		try {
			mutex.lock();
			E res = list.poll();
			producerWaiting.signal();
			return res;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		try {
			mutex.lock();
			while (list.isEmpty()) {
				consumerWaiting.await();
			}
			E res = list.pop();
			producerWaiting.signal();
			return res;
		} finally {
			mutex.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		Instant timeLimit = Instant.now().plus(timeout, unit.toChronoUnit());
		try {
			mutex.lock();
			while (list.isEmpty() && Date.from(Instant.now()).before(Date.from(timeLimit))) {
				consumerWaiting.awaitUntil(Date.from(timeLimit));
			}
			E res = list.poll();
			if (res != null) {
				producerWaiting.signal();
			}

			return res;

		} finally {
			mutex.unlock();
		}

	}

	@Override
	public E element() {
		try {
			mutex.lock();
			return list.getFirst();
		} finally {
			mutex.unlock();
		}

	}

	@Override
	public E peek() {
		try {
			mutex.lock();
			return list.peek();
		} finally {
			mutex.unlock();
		}

	}

}