package telran.multithreading;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

		int numberOfThreads = io.readInt("Enter number of racers", "Wrong number", MIN_NUM_THREADS, 10);
		int distance = io.readInt("Enter distance", "Wrong number", 100, 3500);
		ArrayList<Racer> racerArr = new ArrayList<>(numberOfThreads);
		Race.distance = distance;
		for (int i = 0; i < numberOfThreads; i++) {
			racerArr.add(new Racer(i));
		}
		racerArr.forEach(Racer::start);
		racerArr.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		io.writeLine("Congratulations to thread " + (getWinner(racerArr)));
	}

	private static int getWinner(ArrayList<Racer> racerArr) {
		LocalTime time = LocalTime.MAX;
		int winner = -1;
		for (Racer racer : racerArr) {
			if (racer.finishTime.isBefore(time)) {
				time = racer.finishTime;
				winner = racer.numberOfRacer;
			}
		}
		return winner;
	}
}
