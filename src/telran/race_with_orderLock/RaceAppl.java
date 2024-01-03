package telran.race_with_orderLock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.IntStream;

import telran.view.*;

public class RaceAppl {

	private static final int MAX_THREADS = 10;
	private static final int MIN_THREADS = 3;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;

	public static void main(String[] args) {
		InputOutput io = new SystemInputOutput();
		Item[] items = getItems();
		Menu menu = new Menu("Race Game", items);
		menu.perform(io);

	}

	private static Item[] getItems() {
		Item[] res = { Item.of("Start new game", RaceAppl::startGame), Item.exit() };
		return res;
	}

	static void startGame(InputOutput io) {
		int nThreads = io.readInt("Enter number of the Racers", "Wrong number of the Racers", MIN_THREADS, MAX_THREADS);
		int distance = io.readInt("Enter distance", "Wrong Distance", MIN_DISTANCE, MAX_DISTANCE);
		Race race = new Race(distance, MIN_SLEEP, MAX_SLEEP, new ArrayList<Racer>(), Instant.now());
		Racer[] Racers = new Racer[nThreads];
		startRacers(Racers, race);
		try {
			joinRacers(Racers);
		} catch (InterruptedException e) {

		}
		displayResultsTable(race);
	}

	private static void displayResultsTable(Race race) {
		System.out.println("place\tracer number\ttime");
		ArrayList<Racer> resultsTable = race.getResultsTable();
		IntStream.range(0, resultsTable.size()).mapToObj(i -> toPrintedString(i, race)).forEach(System.out::println);

	}

	private static String toPrintedString(int index, Race race) {
		Racer Racer = race.getResultsTable().get(index);
		return String.format("%3d\t%7d\t\t%d", index + 1, Racer.getRacerId(),
				ChronoUnit.MILLIS.between(race.getStartTime(), Racer.getFinsishTime()));
	}

	private static void joinRacers(Racer[] Racers) throws InterruptedException {
		for (int i = 0; i < Racers.length; i++) {
			Racers[i].join();
		}

	}

	private static void startRacers(Racer[] Racers, Race race) {
		for (int i = 0; i < Racers.length; i++) {
			Racers[i] = new Racer(race, i + 1);
			Racers[i].start();
		}
	}

}
