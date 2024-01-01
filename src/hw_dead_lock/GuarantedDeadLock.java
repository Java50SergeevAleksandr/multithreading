package hw_dead_lock;

import java.util.concurrent.atomic.AtomicInteger;

public class GuarantedDeadLock {

	public static void main(String[] args) throws InterruptedException {
		AtomicInteger stateHolder = new AtomicInteger(0);
		Object mutex1 = new Object();
		Object mutex2 = new Object();

		(new Thread(() -> {
			lockTwoResources(mutex1, mutex2, stateHolder, 1, 3);
		})).start();

		lockTwoResources(mutex2, mutex1, stateHolder, 0, 2);

	}

	private static void lockTwoResources(Object mutex1, Object mutex2, AtomicInteger stateHolder, int desiredState1,
			int desiredState2) {
		waitAndIncrement(stateHolder, desiredState1);
		synchronized (mutex1) {
			System.out.println(Thread.currentThread().getName() + " Under lock: " + mutex1);
			waitAndIncrement(stateHolder, desiredState2);
			synchronized (mutex2) {
				System.out.println(Thread.currentThread().getName() + " Under lock: " + mutex2);
			}
		}
	}

	private static void sleepNoThrow(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void waitAndIncrement(AtomicInteger stateHolder, int desiredState) {
		System.out.println(Thread.currentThread().getName() + " Wait for state: " + desiredState);
		while (stateHolder.get() != desiredState) {
			sleepNoThrow(50);
		}
		System.out.println(Thread.currentThread().getName() + " State set: " + stateHolder.incrementAndGet());
	}
}
