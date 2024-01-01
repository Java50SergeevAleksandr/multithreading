package telran.multithreading;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.*;

public class Racer extends Thread {
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	int numberOfRacer;
	Race race;
	AtomicLong atomicResultTime = new AtomicLong();

	public Racer(int numberOfRacer, Race race) {
		this.numberOfRacer = numberOfRacer + 1;
		this.race = race;
	}

	@Override
	public void run() {
		for (int i = 0; i < race.distance; i++) {
			try {
				sleep(new Random().ints(1, MIN_SLEEP, MAX_SLEEP + 1).sum());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Racer: " + numberOfRacer + ", distance: " + (i + 1) + ", time: "
					+ LocalTime.now().format(DateTimeFormatter.ofPattern("ss:n")));
		}
		race.setWinner(numberOfRacer);
	}

}
