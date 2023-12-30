package hw_dead_lock;

import java.util.ArrayList;
import java.util.stream.IntStream;

import telran.multithreading.Racer;

public class DeadLockApp {
	private static final int numberOfThreads = 20;

	public static void main(String[] args) {
		Object mutex1 = new Object();
		Object mutex2 = new Object();
		ArrayList<Worker> arr = new ArrayList<>(numberOfThreads);
		IntStream.range(0, numberOfThreads).forEach(i -> arr.add(new Worker(mutex1, mutex2)));
		arr.forEach(Worker::start);
	}
}
