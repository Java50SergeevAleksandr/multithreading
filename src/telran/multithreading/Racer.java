package telran.multithreading;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

public class Racer extends Thread {
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	int numberOfRacer;
	LocalTime finishTime;

	public Racer(int numberOfRacer) {
		this.numberOfRacer = numberOfRacer + 1;

	}

	@Override
	public void run() {
		for (int i = 0; i < Race.distance; i++) {
			try {
				sleep(IntStream.range(MIN_SLEEP, MAX_SLEEP + 1).limit(1).sum());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Racer: " + numberOfRacer + ", distance: " + (i + 1) + ", time: "
					+ LocalTime.now().format(DateTimeFormatter.ofPattern("ns")));
		}
		finishTime = LocalTime.now();
	}
}
