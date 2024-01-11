package telran.multithreading.messaging;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class MyLinkedBlockingQueue<E> implements MyBlockingQueue<E> {
	private int capacity;
	private LinkedList<E> list = new LinkedList<>();
	private Lock lock = new ReentrantLock();
	private Condition consumerWaiting = lock.newCondition();
	private Condition producerWaiting = lock.newCondition();

	public MyLinkedBlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	public MyLinkedBlockingQueue() {
		this(14);
	}

	@Override
	public boolean add(E obj) {
		try {
			lock.lock();
			if (list.size() == capacity) {
				throw new IllegalStateException("Element cannot be added at this time due to capacity restrictions");
			}
			list.add(obj);
			consumerWaiting.signal();
			return true;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean offer(E obj) {
		try {
			lock.lock();
			if (list.size() == capacity) {
				return false;
			}
			list.add(obj);
			consumerWaiting.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void put(E obj) throws InterruptedException {
		try {
			lock.lock();
			while (list.size() == capacity) {
				producerWaiting.await();
			}
			list.add(obj);
			consumerWaiting.signal();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public boolean offer(E obj, long timeout, TimeUnit unit) throws InterruptedException {
		Instant timeLimit = Instant.now().plus(timeout, unit.toChronoUnit());
		try {
			lock.lock();
			while (list.size() == capacity && Date.from(Instant.now()).before(Date.from(timeLimit))) {
				producerWaiting.awaitUntil(Date.from(timeLimit));
			}
			if (list.size() == capacity) {
				return false;
			}
			list.add(obj);
			consumerWaiting.signal();
			return true;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E remove() {
		try {
			lock.lock();
			E res = list.pop();
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll() {
		try {
			lock.lock();
			E res = list.poll();
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		try {
			lock.lock();
			while (list.isEmpty()) {
				consumerWaiting.await();
			}
			E res = list.pop();
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) {
		Instant timeLimit = Instant.now().plus(timeout, unit.toChronoUnit());
		try {
			lock.lock();
			while (list.isEmpty() && Date.from(Instant.now()).before(Date.from(timeLimit))) {
				consumerWaiting.awaitUntil(Date.from(timeLimit));
			}
			E res = list.poll();
			producerWaiting.signal();
			return res;

		} catch (InterruptedException e) {
			E res = list.poll();
			producerWaiting.signal();
			return res;
		} finally {
			lock.unlock();
		}

	}

	@Override
	public E element() {
		return list.getFirst();
	}

	@Override
	public E peek() {
		return list.peek();
	}

}