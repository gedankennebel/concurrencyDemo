package Locks;

public class Execeute {
    public static void main(String[] args) {
        Message message = new Message();
        Producer producer = new Producer(message);
        Consumer consumer = new Consumer(message);

        producer.start();
        consumer.start();
    }
}
