package telran.multithreading;

import java.util.stream.IntStream;

public class NumberPrinter extends Thread {
	private static final int SLEEP_TIME = Integer.MAX_VALUE;
	private int counter = 0;
	private int N_PORTIONS;
	private int N_STRING;
	private int threadNumber;
	private Thread nextThread;

	NumberPrinter(int numbersCount, int portion, int threadNumber) {
		N_PORTIONS = portion;
		N_STRING = numbersCount / portion;
		this.threadNumber = threadNumber;
	}

	@Override
	public void run() {
		sleepState();
	}

	private void sleepState() {
		try {
			sleep(SLEEP_TIME);
		} catch (InterruptedException e) {
			printNubers();
			nextThread.interrupt();
			if (++counter != N_STRING) {
				sleepState();
			}
		}

	}

	private void printNubers() {
		IntStream.range(0, N_PORTIONS).forEach(i -> System.out.print(threadNumber));
		System.out.println();
	}

	public void setNextThread(Thread nextThread) {
		this.nextThread = nextThread;
	}
}
