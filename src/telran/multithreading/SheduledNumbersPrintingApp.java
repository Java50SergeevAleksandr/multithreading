package telran.multithreading;

import java.util.stream.IntStream;

public class SheduledNumbersPrintingApp {
	private static final int N_PRINTERS = 4;
	private static final int N_PORTIONS = 5;
	private static final int N_NUMBERS = 20;

	public static void main(String[] args) {
		NumberPrinter[] arr = new NumberPrinter[N_PRINTERS];
		createThreads(arr);
		linkNextThreads(arr);
		for (Thread t : arr) {
			t.start();
		}
		arr[0].interrupt();
	}

	private static void linkNextThreads(NumberPrinter[] arr) {
		IntStream.range(0, N_PRINTERS).forEach(i -> arr[i].setNextThread(arr[getNextThread(i)]));
	}

	private static void createThreads(NumberPrinter[] arr) {
		IntStream.range(0, N_PRINTERS).forEach(i -> arr[i] = new NumberPrinter(N_NUMBERS, N_PORTIONS, i + 1));
	}

	private static int getNextThread(int i) {
		return i < N_PRINTERS - 1 ? i + 1 : 0;
	}
}
