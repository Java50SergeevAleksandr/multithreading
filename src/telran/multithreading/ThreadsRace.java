package telran.multithreading;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.IntStream;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.SystemInputOutput;

public class ThreadsRace {
	private static final int MIN_NUM_THREADS = 3;

	public static void main(String[] args) {
		InputOutput io = new SystemInputOutput();
		ArrayList<Item> items = new ArrayList<>(List.of(Item.of("Start Game", ThreadsRace::startGame), Item.exit()));
		Menu menu = new Menu("Threads Race", items);
		menu.perform(io);
	}

	private static void startGame(InputOutput io) {
		int numberOfThreads = io.readInt("Enter number of racers", "Wrong number", MIN_NUM_THREADS, 500);
		int distance = io.readInt("Enter distance", "Wrong number", 10, 3500);
		ArrayList<Racer> racerArr = new ArrayList<>(numberOfThreads);
		Race race = new Race(distance);
		IntStream.range(0, numberOfThreads).forEach(i -> racerArr.add(new Racer(i, race)));
		startRace(racerArr, race);
		printResults(race);
	}

	private static void startRace(ArrayList<Racer> racerArr, Race race) {
		race.setStartTime();
		racerArr.forEach(Racer::start);
		racerArr.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private static void printResults(Race race) {
		System.out.println("Place   Racer number   Time");
		IntStream.range(0, race.winners.size()).forEach(
				i -> System.out.println((i + 1) + " ".repeat(10) + race.winners.get(i).numberOfRacer + " ".repeat(10)
						+ ChronoUnit.MILLIS.between(race.startTime, race.winners.get(i).finishTime) + " ms"));
	}
}
