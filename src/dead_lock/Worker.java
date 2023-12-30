package dead_lock;

public class Worker extends Thread {
	static final Object mutex1 = new Object();
	static final Object mutex2 = new Object();

	@Override
	public void run() {
		for (int i = 0; i < 100000; i++) {
			f1();
			f2();
		}
	}

	private void f1() {
		/**********
		 * Critical section with two shared resources
		 *************************/
		synchronized (mutex1) {
			synchronized (mutex2) {
				// working with resources
			}
		}
		/************************************************/

	}

	private void f2() {
		/**********
		 * Two critical sections with two separate resources
		 *************************/
		synchronized (mutex1) {
			// critical section with resource 1
		}
		synchronized (mutex2) {
			// critical section with resource 2
		}
		/************************************************/

	}
}
