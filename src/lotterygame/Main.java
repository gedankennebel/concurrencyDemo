package lotterygame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int numberOfParties = ThreadLocalRandom.current().nextInt(2, 10);
        LotteryGame game = new LotteryGame(numberOfParties);
        System.out.println("Starting game with " + numberOfParties + " Threads\n");
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(numberOfParties);

        new Producer(game, blockingQueue).start();

        for (int i = 0; i < numberOfParties; i++) {
            new Consumer(blockingQueue, game).start();
        }
    }
}
