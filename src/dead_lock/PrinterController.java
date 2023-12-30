package dead_lock;

public class PrinterController {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().join();

	}

}