package telran.counters_issue_non_static_synch;

public class CounterResource {
	int counter;

	public void increment() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}
}
