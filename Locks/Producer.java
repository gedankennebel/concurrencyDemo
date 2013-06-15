package Locks;

public class Producer extends Thread {

    private Message message;

    public Producer(Message message) {
        this.message = message;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            message.put(i);
            System.out.println("Producer "
                    + " put: " + i);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
            }
        }
    }
}
