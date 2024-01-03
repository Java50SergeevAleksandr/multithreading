package telran.race_with_orderLock;

import java.time.Instant;

public class Racer extends Thread {
	private Race race;
	private int RacerId;
	private Instant finishTime;

	public Racer(Race race, int RacerId) {
		this.race = race;
		this.RacerId = RacerId;
	}

	public int getRacerId() {
		return RacerId;
	}

	@Override
	public void run() {
		int sleepRange = race.getMaxSleep() - race.getMinSleep() + 1;
		int minSleep = race.getMinSleep();
		int distance = race.getDistance();
		for (int i = 0; i < distance; i++) {
			try {
				sleep((long) (minSleep + Math.random() * sleepRange));
			} catch (InterruptedException e) {
				throw new IllegalStateException();
			}
			System.out.println(RacerId);
		}
		try {
			race.lock.lock();
			finishTime = Instant.now();
			finishRace();
		} finally {
			race.lock.unlock();
		}
		;

	}

	private void finishRace() {
		race.getResultsTable().add(this);

	}

	public Instant getFinsishTime() {
		return finishTime;
	}
}
