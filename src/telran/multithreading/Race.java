package telran.multithreading;

public class Race {
	static int distance;
	static int winner;

	public Race(int distance) {
		Race.distance = distance;
		Race.winner = -1;
	}

	public void setWinner(int numberOfRacer) {
		Race.winner = numberOfRacer;
	}
}
