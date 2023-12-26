package telran.multithreading;

import java.time.LocalTime;
import java.util.ArrayList;

public class Race {
	int distance;
	LocalTime startTime;
	public ArrayList<Racer> winners;

	public Race(int distance) {
		this.distance = distance;
		winners = new ArrayList<>();
	}

	public void setStartTime() {
		startTime = LocalTime.now();
	}

}
