package telran.SheduledPrinting;

import java.util.stream.IntStream;

public class SheduledNumbersPrintingApp {
	private static final int N_PRINTERS = 4;
	private static final int N_PORTIONS = 5;
	private static final int N_NUMBERS = 20;

	public static void main(String[] args) {
		NumberPrinter[] arr = new NumberPrinter[N_PRINTERS];
		createThreads(arr);
		for (Thread t : arr) {
			t.start();
		}
		arr[0].interrupt();
	}

	private static void createThreads(NumberPrinter[] arr) {
		arr[0] = new NumberPrinter(N_NUMBERS, N_PORTIONS, 1);
		IntStream.range(1, N_PRINTERS).forEach(i -> {
			arr[i] = new NumberPrinter(N_NUMBERS, N_PORTIONS, i + 1);
			arr[i - 1].setNextThread(arr[i]);
		});
		arr[arr.length - 1].setNextThread(arr[0]);
	}
}
