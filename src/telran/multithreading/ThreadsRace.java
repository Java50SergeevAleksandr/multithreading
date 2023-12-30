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
		printResults(getWinner(racerArr), io);
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

	private static int getWinner(ArrayList<Racer> racerArr) {
		long time = Long.MAX_VALUE;
		int winner = -1;
		for (Racer r : racerArr) {
			long newTime = r.getResultTime();
			if (newTime < time) {
				time = newTime;
				winner = r.numberOfRacer;
			}
		}
		return winner;
	}

	private static void printResults(int winner, InputOutput io) {
		io.writeLine("Congratulations to thread " + winner);
	}

}
