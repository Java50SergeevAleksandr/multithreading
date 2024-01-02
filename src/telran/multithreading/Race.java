package telran.multithreading;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {
	int distance;
	AtomicInteger winner = new AtomicInteger(-1);
	LocalTime startTime;
	public ArrayList<Racer> winners;

	public Race(int distance) {
		this.distance = distance;
		winners = new ArrayList<>();
	}

	public void setStartTime() {
		startTime = LocalTime.now();
	}	

	public int getWinner() {
		return winner.get();
	}
}
