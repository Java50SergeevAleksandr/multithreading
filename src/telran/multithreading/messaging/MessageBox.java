package telran.multithreading.messaging;

public interface MessageBox {
	void put(String message) throws InterruptedException;
	String take() throws InterruptedException ;
	String poll();
}
