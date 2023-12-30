package hw_dead_lock;

public class Worker extends Thread {
	int res1 = 0;
	int res2 = 0;
	Object mutex1;
	Object mutex2;

	public Worker(Object mutex1, Object mutex2) {
		this.mutex1 = mutex1;
		this.mutex2 = mutex2;
	}

	@Override
	public void run() {
		int res = 0;
		for (int i = 0; i < 10000; i++) {
			res = getRes1() + getRes2();
		}

		System.out.println("Output: " + res);

	}

	private int getRes1() {
		synchronized (mutex1) {
			res1 = res1 + getRes2();
		}
		return res1;
	}

	private int getRes2() {
		synchronized (mutex2) {
			res2++;
			synchronized (mutex1) {
				res1++;
			}
		}
		return res1 + res2;
	}
}
